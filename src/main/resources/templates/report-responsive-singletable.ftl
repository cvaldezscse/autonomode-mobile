<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Test Report</title>
    <link
      rel="preload"
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
      as="style"
      onload="this.onload=null;this.rel='stylesheet'"
    />
    <noscript>
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    </noscript>
    <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #20b2aa;
      }
      .table-custom {
        word-break: break-word;
        table-layout: fixed;
        width: 100%;
        overflow-y: auto;
        max-height: 80vh;
      }
      .rounded-img {
        border-radius: 8px;
        width: 120px; /* Larger thumbnail size */
        height: auto;
        cursor: pointer;
      }
      .text-success {
        color: #28a745 !important;
      }
      .text-danger {
        color: #dc3545 !important;
      }
      .bg-success {
        background-color: #28a745 !important;
        color: white;
      }
      .bg-danger {
        background-color: #dc3545 !important;
        color: white;
      }
      .table th,
      .table td {
        vertical-align: middle;
        text-align: center;
      }
      /* Custom column widths */
      .col-step {
        width: 10%;
      }
      .col-status {
        width: 15%;
      }
      .col-action,
      .col-expected {
        width: 30%;
      }
      .col-proofs {
        width: 15%;
      }
      /* Reduced table margins */
      .container {
        width: 90%; /* Reduce to 80-90% of the page width */
        margin-left: auto;
        margin-right: auto;
      }
    </style>
  </head>
  <body>
    <div class="container my-4">
      <!-- Report Header -->
      <div class="text-center mb-4">
        <img src="irhythm.png" alt="iRhythm Logo" class="img-fluid" style="max-height: 100px" />
        <h1>ZioSuite Mobile App Test Report</h1>
        <h4>Automation Report</h4>
        <p>
          <a href="${repoUrl}" target="_blank">Repository</a> | Date: ${testplan.date}
        </p>
      </div>

      <!-- Execution Summary -->
      <div class="card mb-4">
        <div class="card-header text-center bg-info text-white">
          <h3>Execution Summary</h3>
        </div>
        <div class="card-body">
          <table class="table table-bordered text-center table-custom">
            <thead class="table-light">
              <tr>
                  <th colspan="100%">Execution Time: <strong>${testplan.executionTime}</strong></th>
              </tr>
              <tr>
                <th>Platform</th>
                <th>App Version and Build</th>
                <th>Total Tests</th>
                <th class="text-success">Pass Rate</th>
                <th class="text-danger">Fail Rate</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>${platform}</td>
                <td>${appVersionPlusBuild}</td>
                <td>${testplan.tests?size}</td>
                <td class="text-success">${testplan.passRate}% (${testplan.pass})</td>
                <td class="text-danger">${testplan.failRate}% (${testplan.fail})</td>
              </tr>
              <tr>
                <th colspan="100%">Execution Summary</th>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- Test Results Table -->
      <div class="card mb-4">
        <div class="card-body">
          <#list testplan.tests?keys as key>
            <#assign test = testplan.tests[key] />
            <!-- Test Name as the Title and a clickable hyperlink -->
            <div class="mb-4">
              <h5>
                <a href="#test${key}" class="text-decoration-none">[${test.globalId}] ${test.name}</a>
              </h5>
              <table class="table table-bordered table-custom">
                <thead class="table-secondary">
                  <tr>
                    <th class="col-step">#</th> <!-- Step Number -->
                    <th class="col-action">Action</th> <!-- Action -->
                    <th class="col-expected">Expected</th> <!-- Expected -->
                    <th class="col-proofs">Proofs</th> <!-- Proofs -->
                    <th class="col-status">Status</th> <!-- Status -->
                  </tr>
                </thead>
                <tbody>
                  <#list test.steps?keys as stepKey>
                    <#assign testStep = test.steps[stepKey] />
                    <tr>
                      <td>${stepKey}</td> <!-- Step Number Column -->
                      <td>${testStep.action}</td>
                      <td>${testStep.expected}</td>
                      <td>
                        <div class="row">
                          <#list testStep.screenshots?keys as screenshotKey>
                            <#assign screenshot = testStep.screenshots[screenshotKey] />
                            <div class="col-sm-4 mb-2">
                              <img
                                src="screenshots/${screenshot.name}"
                                class="img-fluid rounded-img"
                                alt="Screenshot"
                                data-bs-toggle="modal"
                                data-bs-target="#screenshotModal${key}-${stepKey}-${screenshotKey}"
                              />
                              <!-- Modal -->
                              <div
                                class="modal fade"
                                id="screenshotModal${key}-${stepKey}-${screenshotKey}"
                                tabindex="-1"
                                aria-labelledby="screenshotModalLabel${key}-${stepKey}-${screenshotKey}"
                                aria-hidden="true"
                              >
                                <div class="modal-dialog modal-dialog-centered modal-lg">
                                  <div class="modal-content">
                                    <div class="modal-header">
                                      <h5 class="modal-title" id="screenshotModalLabel${key}-${stepKey}-${screenshotKey}">
                                        Screenshot
                                      </h5>
                                      <button
                                        type="button"
                                        class="btn-close"
                                        data-bs-dismiss="modal"
                                        aria-label="Close"
                                      ></button>
                                    </div>
                                    <div class="modal-body">
                                      <img src="screenshots/${screenshot.name}" alt="Screenshot" class="img-fluid" />
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </#list>
                        </div>
                      </td>
                      <!-- Status (PASS/FAIL) with color-coding -->
                      <td class="
                        <#if testStep.result == 'PASS'>
                          bg-success
                        <#else>
                          bg-danger
                        </#if>
                      ">
                        <strong>${testStep.result}</strong>
                      </td>
                    </tr>
                  </#list>
                </tbody>
              </table>
            </div>
          </#list>
        </div>
      </div>
    </div>

    <!-- Bootstrap JS for interactivity -->
    <script
      defer
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
    ></script>
  </body>
</html>