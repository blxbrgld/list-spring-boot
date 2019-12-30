/**
 * ConfigurationService
 * @author blxbrgld
 */
export class ConfigurationService {

  private _api: string;

  /**
   * Sets a config property
   * @param property The property's name
   * @param value The property's value
   */
  static set(property, value) {
    this['_' + property] = value;
  }

  /**
   * Gets a property's value
   * @param property The property's name
   * @returns The property's value
   */
  static get(property) {
    return this['_' + property];
  }
}
