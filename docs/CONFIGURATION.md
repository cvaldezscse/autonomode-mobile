# Configuration Reference (`configuration.yml`)

This document describes all the configuration options available in `configuration.yml` for this project.

---

## 1. General Configs

These settings determine the target platform and execution environment.

| Parameter             | Type    | Possible Values               | Description                                                   |
|-----------------------|---------|-------------------------------|---------------------------------------------------------------|
| `executionPlatform`   | string  | `android`, `ios`              | Platform on which tests will run.                             |
| `executionType`       | string  | `local`, `aws`, `saucelabs`   | Where tests are executed: locally, in AWS, or on Sauce Labs.  |

[Configuration File](configuration.yml)

---

## 2. Test Report Config

Controls how the HTML test report is generated and presented.

| Parameter              | Type     | Description                                                                          |
|------------------------|----------|--------------------------------------------------------------------------------------|
| `htmlReportFileName`   | string   | Base name for the generated HTML report file (without `.html`).                      |
| `showFailedTestsFirst` | boolean  | If `true`, failed tests appear at the top of the report.                             |
| `projectRepositoryUrl` | string   | URL of the project’s GitHub repository (used in report links).                       |
| `usedTemplate`         | string   | Name of the FreeMarker template (`.ftl`) used to render the report.                  |
| `logoImage`         | string   | Name of the image that is going to appear in the top of the report as logo (`.png`). |

[Configuration File](configuration.yml)

---

## 3. Android Local Capabilities

Specifies the Appium capabilities for local Android testing.

| Parameter                | Type    | Description                                                               |
|--------------------------|---------|---------------------------------------------------------------------------|
| `androidDeviceName`      | string  | Name of the Android emulator or device (e.g. `Pixel 3a`).                  |
| `androidPlatformVersion` | string  | Android OS version to target (e.g. `"12"`).                               |
| `androidAppFileName`     | string  | APK file name to install (e.g. `ZioMd-1.4.0-901-MCT-rc.apk`).             |
| `androidAppVersion`      | string  | Application version (e.g. `"1.4.0"`).                                     |
| `androidAppBuild`        | string  | Build number of the app (e.g. `"901"`).                                   |

[Configuration File](configuration.yml)
---

## 4. iOS Local Capabilities

Defines the Appium capabilities for local iOS testing.

| Parameter             | Type    | Description                                                                  |
|-----------------------|---------|------------------------------------------------------------------------------|
| `iosDeviceName`       | string  | Name of the iOS device (e.g. `iPhone 11 iRhythm`).                           |
| `iosPlatformVersion`  | string  | iOS version to target (e.g. `"18.3.1"`).                                     |
| `iosDeviceUID`        | string  | UDID of the device (e.g. `00008030-0005694A14E8802E`).                        |
| `xcodeOrgId`          | string  | Xcode Team ID used for signing.                                              |
| `xcodeSigningId`      | string  | Signing identity (e.g. `Apple Development`).                                 |
| `iosAppFileName`      | string  | IPA file name to install (e.g. `ziomd-mct_saucelabs-v1-4-0-build-12.ipa`).   |
| `iosBundleId`         | string  | Bundle identifier of the app (e.g. `com.irhythm.ZioMD.mct`).                 |
| `iosAppVersion`       | string  | Application version (e.g. `"1.4.0"`).                                        |
| `iosAppBuild`         | string  | Build number of the app (e.g. `"12"`).                                       |
| `defaultFontSetting`  | string  | *(Optional)* Default UI font size setting (if uncommented).                  |

**[Configuration File](configuration.yml)
---

## 5. Appium Server Local Config

Settings for starting or connecting to a local Appium server.

| Parameter                         | Type     | Description                                                                                   |
|-----------------------------------|----------|-----------------------------------------------------------------------------------------------|
| `localServerDynamicallyExecuted`  | boolean  | If `true`, spin up Appium server dynamically; otherwise expect an existing server instance.    |
| `appiumServerPort`                | integer  | Port on which the local Appium server listens (e.g. `3393`).                                  |
| `appiumServerUrl`                 | string   | Base URL for local Appium server (e.g. `http://127.0.0.1`).                                   |

[Configuration File](configuration.yml)
---

## 6. Jama Configuration *(to be removed)*

Legacy settings for integrating with Jama. **This section is slated for removal in future releases.**

| Parameter                     | Type    | Description                                                           |
|-------------------------------|---------|-----------------------------------------------------------------------|
| `jamaConfiguration.jamaEnabled`        | string  | Enable Jama integration (`'true'` or `'false'`).                  |
| `jamaConfiguration.testGroup`          | string  | ID of the test group in Jama.                                      |
| `jamaConfiguration.testCycle`          | string  | ID of the test cycle in Jama.                                      |
| `jamaConfiguration.testRun`            | string  | ID of the test run in Jama.                                        |
| `jamaConfiguration.jamaProject`        | string  | ID of the Jama project.                                            |
| `jamaConfiguration.runRegression`      | string  | Whether to run regression (`'true'` or `'false'`).                  |
| `jamaConfiguration.updateTestRunStatus`| string  | Whether to update test run status in Jama (`'true'` or `'false'`). |
| `jamaConfiguration.filterByTags`       | string  | Whether to filter cases by tags (`'true'` or `'false'`).           |
| `jamaConfiguration.assignedTo`         | integer | User ID to assign status updates to.                               |
| `jamaConfiguration.tags`               | string  | Comma-separated list of tags for filtering.                        |

[Configuration File](configuration.yml)