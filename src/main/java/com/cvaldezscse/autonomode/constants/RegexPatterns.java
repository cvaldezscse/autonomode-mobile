package com.cvaldezscse.autonomode.constants;

public class RegexPatterns {

    /**
     * Regex pattern for birthdate in numeric format: MM/DD/YYYY or M/D/YYYY
     * Matches dates like: 01/01/2000, 1/1/2000, 12/31/1999
     * - Month: 1-12 with optional leading zero
     * - Day: 1-31 with optional leading zero
     * - Year: 4 digits
     */
    public static final String REGEX_NUMERIC_DATE_PATTERN =
            "^(0?[1-9]|1[0-2])/(0?[1-9]|1[0-9]|2[0-9]|3[0-1])/\\d{4}$";

    /**
     * Regex pattern for birthdate with month abbreviation: MMM DD, YYYY
     * Matches dates like: Jan 01, 2000, Feb 1, 2000, Dec 31, 1999
     * - Month: three-letter abbreviation (Jan, Feb, etc.)
     * - Day: 1-31 with optional leading zero
     * - Year: 4 digits
     */
    public static final String REGEX_ABBREVIATED_DATE_PATTERN =
            "^(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s+(0?[1-9]|1[0-9]|2[0-9]|3[0-1]),\\s+\\d{4}$";


    /**
     * Combined regex pattern for birthdate that accepts both numeric and abbreviated formats
     * Matches both MM/DD/YYYY and MMM DD, YYYY formats
     */
    public static final String REGEX_BIRTHDATE_PATTERN =
            "^(?:(0?[1-9]|1[0-2])/(0?[1-9]|1[0-9]|2[0-9]|3[0-1])/\\d{4}|" +
                    "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s+(0?[1-9]|1[0-9]|2[0-9]|3[0-1]),\\s+\\d{4})$";

    /**
     * Regex pattern for names in "LastName, FirstName" format
     * Allows for multi-part names, hyphens, apostrophes and spaces
     */
    public static final String REGEX_NAME_PATTERN =
            "^([\\p{L}\\-']+[,.]?\\s*)+,\\s*([\\p{L}\\-']+\\s*)+$";

    /**
     * Regex for "LastName, FirstName" format
     * Supports:
     * - Compound last names and first names
     * - Accented and special characters (letters from any language)
     * - Apostrophes, hyphens, and spaces
     * - Multiple spaces between words
     * Valid name examples:
     * - "Smith, John"
     * - "García, José María"
     * - "O'Connor, Mary"
     * - "Williams-Johnson, Sarah Ann"
     * - "von Neumann, John"
     * - "De La Cruz, Juan Carlos"
     */
    public static final String REGEX_LASTNAME_FIRSTNAME_PATTERN =
            "^([\\p{L}\\-'`]+(\\s+[\\p{L}\\-'`]+)*),\\s+([\\p{L}\\-'`]+(\\s+[\\p{L}\\-'`]+)*)$";

    public static final String REGEX_PHONE_FORMAT = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$";
    public static final String REGEX_RELATIVE_DATE_FORMAT =
            "^\\d+\\s+(?i)(min|mins|minute|minutes|hr|hrs|hour|hours|day|days|week|weeks|month|months|year|years)\\s+ago$";
}
