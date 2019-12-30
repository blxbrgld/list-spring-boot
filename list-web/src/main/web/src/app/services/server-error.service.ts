import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { HttpErrorResponse } from "@angular/common/http";

/**
 * ServerError object
 * @author blxbrgld
 */
export class ServerError {

  private _property: string;
  private _message: string;

  /**
   * Constructor
   * @param property The property
   * @param message The message
   */
  constructor(property, message) {
    this._property = property;
    this._message = message;
  }

  /**
   * @returns The property
   */
  get property(): string {
    return this._property;
  }

  /**
   * @returns The message
   */
  get message(): string {
    return this._message;
  }
}

/**
 * Server error service to read server error response and bind messages to a form in interest
 * @author blxbrgld
 */
@Injectable({
  providedIn: 'root'
})
export class ServerErrorService {

  /**
   * Handle the {@link HttpErrorResponse} messages and bind form errors to the given form
   * @param form {@link FormGroup}
   * @param error {@link Error}
   */
  handle(form: FormGroup, error: any) {
    if (error instanceof HttpErrorResponse && error.status === 400) {
      const serverError = this.messageToServerError(error.error.message);
      const formControl = form.get(serverError.property);
      if(formControl) {
        formControl.setErrors({
          serverError: serverError.message
        })
      }
    }
  }

  /**
   * Convert a string message like "create.title: The title already exists." to a ServerError object
   * @param message The message
   * @returns The {@link ServerError}
   */
  private messageToServerError(message: string) {
    const t = message.split(":");
    const p = t[0].split(".");
    return new ServerError(p[p.length-1], t[1].trim());
  }
}
