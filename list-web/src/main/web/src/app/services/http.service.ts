import { Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConfigurationService } from "./configuration.service";

/**
 * HttpService
 * @author blxbrgld
 */
export class HttpService {

  resource: string;
  type: any;

  /**
   * Constructor
   * @param http {@link HttpClient}
   */
  constructor(@Inject(HttpClient) private http: HttpClient) {}

  /**
   * Constructs the full resource url
   * @returns The constructed url
   */
  get url() {
    return ConfigurationService.get('api') + this.resource;
  }

  /**
   * Gets an object by id
   * @param id The id
   * @returns The object
   */
  get<T>(id: number) {
    return this.http.get<T>(this.url + '/' + id);
  }

  /**
   * Gets list of objects
   * @returns List of objects
   */
  list<T>() {
    return this.http.get<T>(this.url);
  }

  /**
   * Creates an object
   * @param body The object to create
   * @returns Observable of the response
   */
  create<T>(body: any) {
    return this.http.post<T>(this.url, body);
  }

  /**
   * Updates an object
   * @param id The object's id
   * @param body The object to update
   * @returns Observable of the response
   */
  update<T>(id: number, body: any) {
    return this.http.put<T>(this.url + '/' + id, body);
  }

  /**
   * Deletes an object by id
   * @param id The object's id
   * @returns Observable of the response
   */
  delete<T>(id: number) {
    return this.http.delete<T>(this.url + '/' + id);
  }
}
