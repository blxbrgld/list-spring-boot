import { Injectable } from '@angular/core';

/**
 * AlertService
 * @author blxbrgld
 */
@Injectable({
  providedIn: 'root'
})
export class AlertService {

  show: boolean = false;
  type: string = 'alert-info';
  message: string;
  timer: any;

  /**
   * Alerts a message
   * @param message The alert's message
   * @param type The alert's type
   * @param duration The alert's duration
   */
  alert(message: string, type: string = 'alert-info', duration: number = 3000) {
    this.show = true;
    this.type = type;
    this.message = message;
    if(this.timer) {
      clearTimeout(this.timer);
    }
    if(duration) {
      this.timer = setTimeout(() => {
        this.close();
      }, duration);
    }
  }

  /**
   * Hides the alert
   */
  close() {
    this.show = false;
  }
}
