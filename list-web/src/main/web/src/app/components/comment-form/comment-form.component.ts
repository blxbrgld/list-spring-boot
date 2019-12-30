import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AlertService } from "../../services/alert.service";
import { Comment, CommentsService } from "../../services/comments.service";
import { ServerErrorService } from "../../services/server-error.service";
import { Messages } from "../../utils/messages.utils";

/**
 * CommentFormComponent
 * @author blxbrgld
 */
@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.css']
})
export class CommentFormComponent implements OnInit {

  commentForm: FormGroup;
  comment: Comment;

  /**
   * Constructor
   * @param commentsService {@link CommentsService}
   * @param alertService {@link AlertService}
   * @param serverErrorService {@link ServerErrorService}
   * @param formBuilder {@link FormBuilder}
   * @param route {@link ActivatedRoute}
   * @param router {@link Router}
   */
  constructor(
    private commentsService: CommentsService,
    private alertService: AlertService,
    private serverErrorService: ServerErrorService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Group of controls for the comment form
    this.commentForm = this.formBuilder.group({
      id: [''],
      title: ['', Validators.required]
    });
  }

  /**
   * Sets the form's value (existing comment or a new object)
   */
  ngOnInit() {
    this.route.params.map((params: Params) => params.id).subscribe(id => {
      if(id) {
        this.commentsService.get<Comment>(id).subscribe(comment => {
          this.commentForm.setValue(comment);
          this.comment = comment;
        });
      } else {
        this.comment = new Comment();
      }
    });
  }

  /**
   * Create or update the comment
   */
  submit() {
    if(this.comment.id) {
      this.commentsService.update<Comment>(this.comment.id, this.commentForm.value).subscribe({
        next: () => {
          this.alertService.alert(Messages.COMMENT_UPDATE_SUCCESS);
          this.router.navigate(['/comments']);
        },
        error: (err: any) => {
          this.serverErrorService.handle(this.commentForm, err);
        }
      });
    } else {
      this.commentsService.create<Comment>(this.commentForm.value).subscribe({
        next: () => {
          this.alertService.alert(Messages.COMMENT_CREATE_SUCCESS);
          this.router.navigate(['/comments']);
        },
        error: (err: any) => {
          this.serverErrorService.handle(this.commentForm, err);
        }
      });
    }
  }

  /**
   * Delete the comment and navigate to the comments list
   */
  delete() {
    //TODO Confirmation dialog
    this.commentsService.delete<Comment>(this.comment.id).subscribe(response => {
      this.alertService.alert(Messages.COMMENT_DELETE_SUCCESS);
      this.router.navigate(['/comments']);
    });
  }

  /**
   * Just navigate to the comments list
   */
  cancel() {
    this.router.navigate(['/comments']);
  }
}
