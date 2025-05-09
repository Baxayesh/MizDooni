/**
 * Generated by orval v6.29.1 🍺
 * Do not edit manually.
 * Mizdooni
 * OpenAPI spec version: 1.0.0
 */
import type { LocalTime } from './localTime';

export interface AddRestaurantRequest {
  /**
   * @minLength 2
   * @maxLength 128
   */
  city?: string;
  closeTime?: LocalTime;
  /**
   * @minLength 2
   * @maxLength 128
   */
  country?: string;
  description?: string;
  image?: string;
  name: string;
  openTime?: LocalTime;
  /**
   * @minLength 2
   * @maxLength 128
   */
  street?: string;
  type: string;
}
