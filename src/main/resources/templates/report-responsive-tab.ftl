<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>${markupTitle!''}</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link
    rel="preload"
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
    as="style"
    onload="this.onload=null;this.rel='stylesheet'"
  />
  <noscript>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
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
    }
    .rounded-img {
      border-radius: 8px;
      width: 200px;
      height: auto;
      cursor: pointer;
    }
    .text-success {
      color: #28a745 !important;
    }
    .text-danger {
      color: #dc3545 !important;
    }
    .tabs-container {
      overflow-x: auto;
    }
    .nav-tabs .nav-link {
      white-space: nowrap;
    }
  </style>
</head>
<body>
  <div class="container my-4">
    <!-- Report Header -->
    <div class="text-center mb-4">
      <img src="irhythm.png" alt="iRhythm Logo" class="img-fluid" style="max-height: 100px"/>
      <h1>ZioSuite Mobile App Test Report</h1>
      <h4>Automation Report</h4>
      <p>
        <a href="${repoUrl}" target="_blank">Repository</a> |
        Date: ${testplan.date}
      </p>
    </div>

    <!-- Execution Summary -->
    <div class="card mb-4">
      <div class="card-header text-center bg-info text-white">
        <h3>Execution Summary</h3>
      </div>
      <div class="card-body">
        <table class="table table-bordered text-center table-custom mb-0">
          <thead class="table-light">
            <tr>
              <th colspan="5">Execution Time: <strong>${testplan.executionTime}</strong></th>
            </tr>
            <tr>
              <th>Platform</th>
              <th>App Version&amp;Build</th>
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
          </tbody>
        </table>
      </div>
    </div>

    <!-- Test Results Tabbed Layout -->
    <div class="mb-4">
      <!-- Search Bar -->
      <div class="mb-3">
        <input id="tabSearch" type="text" class="form-control" placeholder="Search test case..." />
      </div>

      <!-- Nav Tabs -->
      <div class="tabs-container mb-3">
        <ul class="nav nav-tabs" id="testTabs" role="tablist">
          <#list testplan.tests?values as test>
            <#assign idx = test?index>
            <li class="nav-item" role="presentation" data-test-title="${test.globalId} ${test.name}">
              <a
                class="nav-link${((idx == 0)?then(' active',''))}"
                id="tab-${idx}-test"
                data-bs-toggle="tab"
                href="#test-${idx}"
                role="tab"
                aria-controls="test-${idx}"
                aria-selected="${(idx == 0)?string('true','false')}"
              >${test.globalId}</a>
            </li>
          </#list>
        </ul>
      </div>

      <!-- Tab Panes -->
      <div class="tab-content" id="testTabsContent">
        <#list testplan.tests?values as test>
          <#assign idx = test?index>
          <div
            class="tab-pane fade${((idx == 0)?then(' show active',''))}"
            id="test-${idx}"
            role="tabpanel"
            aria-labelledby="tab-${idx}-test"
          >
            <div class="card mb-4">
              <div class="card-body">
                <h5>[${test.globalId}] ${test.name}</h5>
                <p><strong>Description:</strong> ${test.description}</p>
                <p>
                  <strong>Test Case Link:</strong>
                  <a href="${test.url}" target="_blank">${test.url}</a>
                </p>
                <table class="table table-bordered table-custom mb-0">
                  <thead class="table-secondary">
                    <tr>
                      <th>#</th>
                      <th>Action</th>
                      <th>Expected</th>
                      <th>Proofs</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    <#list test.steps?values as step>
                      <tr>
                        <td>${step?index + 1}</td>
                        <td>${step.action}</td>
                        <td>${step.expected}</td>
                        <td>
                          <div class="row">
                            <#list step.screenshots?values as ss>
                              <#assign sk = ss?index>
                              <div class="col-sm-4 mb-2">
                                <img
                                  src="screenshots/${ss.name}"
                                  class="img-fluid rounded-img"
                                  alt="Screenshot"
                                  data-bs-toggle="modal"
                                  data-bs-target="#screenshotModal-${idx}-${step?index}-${sk}"
                                />
                                <div
                                  class="modal fade"
                                  id="screenshotModal-${idx}-${step?index}-${sk}"
                                  tabindex="-1"
                                  aria-labelledby="screenshotModalLabel-${idx}-${step?index}-${sk}"
                                  aria-hidden="true"
                                >
                                  <div class="modal-dialog modal-dialog-centered modal-lg">
                                    <div class="modal-content">
                                      <div class="modal-header">
                                        <h5 class="modal-title" id="screenshotModalLabel-${idx}-${step?index}-${sk}">Screenshot</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                      </div>
                                      <div class="modal-body">
                                        <img src="screenshots/${ss.name}" alt="Screenshot" class="img-fluid" />
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </#list>
                          </div>
                        </td>
                        <td class="${((step.result == 'PASS')?then('bg-success text-dark','bg-danger text-dark'))}">
                          <strong>${step.result}</strong>
                        </td>
                      </tr>
                    </#list>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </#list>
      </div>
    </div>
  </div>

  <script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
  <script>
    document.addEventListener('DOMContentLoaded', function() {
      const searchInput = document.getElementById('tabSearch');
      searchInput.addEventListener('input', () => {
        const filter = searchInput.value.toLowerCase();
        document.querySelectorAll('#testTabs li.nav-item').forEach(li => {
          const title = li.getAttribute('data-test-title').toLowerCase();
          li.style.display = title.includes(filter) ? '' : 'none';
        });
      });
    });
  </script>
</body>
</html>