import { Component } from '@angular/core';
import {AlertService} from "../../services/alert.service";

/**
 * AlertComponent
 * @author blxbrgld
 */
@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent {

  /**
   * Constructor
   * @param alertService {@link AlertService}
   */
  constructor(private alertService: AlertService) {}
}
