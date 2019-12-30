import { Injectable } from '@angular/core';
import { HttpService } from "./http.service";

/**
 * Subtitle object
 * @author blxbrgld
 */
export class Subtitle {
  id: number;
  title: string;
}

/**
 * SubtitlesService
 * @author blxbrgld
 */
@Injectable({
  providedIn: 'root'
})
export class SubtitlesService extends HttpService {

  resource: string = '/subtitles';
}
