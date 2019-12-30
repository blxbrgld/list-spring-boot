import { Component, OnInit } from '@angular/core';

import { AlertService } from "../../services/alert.service";
import { SubtitlesService, Subtitle } from '../../services/subtitles.service';
import { Messages } from "../../utils/messages.utils";

/**
 * SubtitlesComponent
 * @author blxbrgld
 */
@Component({
  selector: 'app-subtitles',
  templateUrl: './subtitles.component.html',
  styleUrls: ['./subtitles.component.css']
})
export class SubtitlesComponent implements OnInit {

  subtitles: Subtitle[];

  /**
   * Constructor
   * @param subtitlesService {@link SubtitlesService}
   * @param alertService {@link AlertService}
   */
  constructor(
    private subtitlesService: SubtitlesService,
    private alertService: AlertService
  ) {}

  /**
   * Loads the subtitles list
   */
  ngOnInit() {
    this.loadList();
  }

  /**
   * Deletes subtitle by id
   * @param id The subtitle id
   */
  delete(id: number) {
    //TODO Confirmation dialog
    this.subtitlesService.delete<Subtitle>(id).subscribe(response => {
      this.alertService.alert(Messages.SUBTITLE_DELETE_SUCCESS);
      this.loadList(); // Reload the subtitles list
    });
  }

  /**
   * REST call to load the subtitles list
   */
  loadList() {
    this.subtitlesService.list<Array<Subtitle>>().subscribe(subtitles => this.subtitles = subtitles);
  }
}
