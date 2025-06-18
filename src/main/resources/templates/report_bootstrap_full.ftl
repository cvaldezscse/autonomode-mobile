<html>
<head>
<meta charset="UTF-8">
  <title>${markupTitle}</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    table { word-break: break-word; }
.status-pass { color: #28a745; font-weight: bold; }
.status-fail { color: #dc3545; font-weight: bold; }
.screenshot-thumb { cursor: pointer; }
</style>
</head>
<body class="bg-light text-dark">
  <div class="container py-4">
    <!-- Report Header -->
    <div class="text-center mb-4">
      <img src="irhythm.png" alt="iRhythm" class="img-fluid mb-3" style="max-height:100px;">
      <h2>ZioSuite Mobile App Test Report</h2>
      <p class="text-muted">${testplan.date}</p>
      <a href="${repoUrl}" class="btn btn-outline-primary btn-sm mb-3">View Repository</a>
    </div>

    <!-- Execution Summary -->
    <div class="card mb-4">
      <div class="card-header bg-primary text-white">
        Execution Summary
      </div>
      <div class="card-body">
        <table class="table table-striped mb-0 text-center align-middle">
          <thead>
            <tr>
              <th>Platform</th>
              <th>Environment</th>
              <th>App Version</th>
              <th>Time Elapsed</th>
              <th>Tests Completed</th>
              <th>Pass Rate</th>
              <th>Fail Rate</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>${platform}</td>
              <td>${environment}</td>
              <td>${appVersionPlusBuild}</td>
              <td>${testplan.executionTime}</td>
              <td>${testplan.tests?size}</td>
              <td class="status-pass">${testplan.passRate}% (${testplan.pass})</td>
              <td class="status-fail">${testplan.failRate}% (${testplan.fail})</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Detailed Test Results -->
    <#list testplan.tests?keys as key>
      <#assign test = testplan.tests[key]/>
      <div class="card mb-3">
        <div class="card-header" style="background-color:${test.color};color:#fff;">
          <strong>Test Case ID:</strong> ${test.globalId} &mdash;
          <a href="${test.url}" target="_blank" class="text-white text-decoration-underline">[Link]</a><br>
          <strong>Name:</strong> ${test.name}<br>
          <strong>Description:</strong> ${test.description}
        </div>
        <div class="card-body p-0">
          <table class="table table-bordered mb-0 align-middle">
            <thead class="table-light">
              <tr>
                <th style="width:5%;">#</th>
                <th style="width:25%;">Action</th>
                <th style="width:25%;">Expected</th>
                <th style="width:25%;">Evidence</th>
                <th style="width:20%;">Status</th>
              </tr>
            </thead>
            <tbody>
              <#list test.steps?keys as stepKey>
                <#assign step = test.steps[stepKey]/>
                <tr>
                  <td class="text-center">${stepKey}</td>
                  <td>${step.action}</td>
                  <td>${step.expected}</td>
                  <td>
                    <#list step.logs?keys as logKey>
                      ${step.logs[logKey]}<br>
                    </#list>
                    <#list step.screenshots?keys as ssKey>
                      <img src="screenshots/${step.screenshots[ssKey].name}"
                           class="img-fluid mb-2 screenshot-thumb"
                           style="max-height:150px;">
                    </#list>
                  </td>
                  <td class="text-center">
                    <span class="${(step.result == 'PASS')?string('status-pass','status-fail')}">${step.result}</span>
                  </td>
                </tr>
              </#list>
            </tbody>
          </table>
        </div>
      </div>
    </#list>

  </div>

  <!-- Image Preview Modal -->
  <div class="modal fade" id="imageModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
      <div class="modal-content bg-transparent border-0">
        <div class="modal-body p-0 text-center">
          <img id="modalImage" src="" class="img-fluid" />
        </div>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
  <script>
    document.addEventListener('DOMContentLoaded', function() {
var modalEl = document.getElementById('imageModal');
var bsModal = new bootstrap.Modal(modalEl);
document.querySelectorAll('.screenshot-thumb').forEach(function(img) {
img.addEventListener('click', function() {
document.getElementById('modalImage').src = img.src;
bsModal.show();
});
      });
    });
  </script>
</body>
</html>