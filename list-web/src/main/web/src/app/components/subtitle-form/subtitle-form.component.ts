import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import 'rxjs/add/operator/map';

import { AlertService } from "../../services/alert.service";
import { Messages } from "../../utils/messages.utils";
import { ServerErrorService } from "../../services/server-error.service";
import { Subtitle, SubtitlesService } from "../../services/subtitles.service";

/**
 * SubtitleFormComponent
 * @author blxbrgld
 */
@Component({
  selector: 'app-subtitle-form',
  templateUrl: './subtitle-form.component.html',
  styleUrls: ['./subtitle-form.component.css']
})
export class SubtitleFormComponent implements OnInit {

  subtitleForm: FormGroup;
  subtitle: Subtitle;

  /**
   * Constructor
   * @param subtitlesService {@link SubtitlesService}
   * @param alertService {@link AlertService}
   * @param serverErrorService {@link ServerErrorService}
   * @param formBuilder {@link FormBuilder}
   * @param route {@link ActivatedRoute}
   * @param router {@link Router}
   */
  constructor(
    private subtitlesService: SubtitlesService,
    private alertService: AlertService,
    private serverErrorService: ServerErrorService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Group of controls for the subtitle form
    this.subtitleForm = this.formBuilder.group({
      id: [''],
      title: ['', Validators.required]
    });
  }

  /**
   * Sets the form's value (existing subtitle or a new object)
   */
  ngOnInit() {
    this.route.params.map((params: Params) => params.id).subscribe(id => {
      if(id) {
        this.subtitlesService.get<Subtitle>(id).subscribe(subtitle => {
          this.subtitleForm.setValue(subtitle);
          this.subtitle = subtitle;
        });
      } else {
        this.subtitle = new Subtitle();
      }
    });
  }

  /**
   * Create or update the subtitle
   */
  submit() {
    if(this.subtitle.id) {
      this.subtitlesService.update<Subtitle>(this.subtitle.id, this.subtitleForm.value).subscribe({
        next: () => {
          this.alertService.alert(Messages.SUBTITLE_UPDATE_SUCCESS);
          this.router.navigate(['/subtitles']);
        },
        error: (err: any) => {
          this.serverErrorService.handle(this.subtitleForm, err);
        }
      });
    } else {
      this.subtitlesService.create<Subtitle>(this.subtitleForm.value).subscribe({
        next: () => {
          this.alertService.alert(Messages.SUBTITLE_CREATE_SUCCESS);
          this.router.navigate(['/subtitles']);
        },
        error: (err: any) => {
          this.serverErrorService.handle(this.subtitleForm, err);
        }
      });
    }
  }

  /**
   * Delete the subtitle and navigate to the subtitles list
   */
  delete() {
    //TODO Confirmation dialog
    this.subtitlesService.delete<Subtitle>(this.subtitle.id).subscribe(response => {
      this.alertService.alert(Messages.SUBTITLE_DELETE_SUCCESS);
      this.router.navigate(['/subtitles']);
    });
  }

  /**
   * Just navigate to the subtitles list
   */
  cancel() {
    this.router.navigate(['/subtitles']);
  }
}
