import { Component, OnInit } from '@angular/core';

import { AlertService } from "../../services/alert.service";
import { CommentsService, Comment } from '../../services/comments.service';
import { Messages } from "../../utils/messages.utils";

/**
 * CommentsComponent
 * @author blxbrgld
 */
@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  comments: Comment[];

  /**
   * Constructor
   * @param commentsService {@link CommentsService}
   * @param alertService {@link AlertService}
   */
  constructor(
    private commentsService: CommentsService,
    private alertService: AlertService
  ) {}

  /**
   * Loads the comments list
   */
  ngOnInit() {
    this.loadList();
  }

  /**
   * Deletes comment by id
   * @param id The comment id
   */
  delete(id: number) {
    //TODO Confirmation dialog
    this.commentsService.delete<Comment>(id).subscribe(response => {
      this.alertService.alert(Messages.COMMENT_DELETE_SUCCESS);
      this.loadList(); // Reload the comments list
    });
  }

  /**
   * REST call to load the comments list
   */
  loadList() {
    this.commentsService.list<Array<Comment>>().subscribe(comments => this.comments = comments);
  }
}
