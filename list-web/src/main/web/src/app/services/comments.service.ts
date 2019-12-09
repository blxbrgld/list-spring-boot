import { Injectable } from '@angular/core';
import { HttpService } from "./http.service";

/**
 * Comment object
 * @author blxbrgld
 */
export class Comment {
  id: number;
  title: string;
}

/**
 * CommentsService
 * @author blxbrgld
 */
@Injectable({
  providedIn: 'root'
})
export class CommentsService extends HttpService {

  resource: string = '/comments';
}
