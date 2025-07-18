<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>${markupTitle!''}</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    table { word-break: break-word; }
    .screenshot-thumb { cursor: pointer; max-height: 80px; }
  </style>
</head>
<body class="bg-light text-dark">
  <div class="container py-4">
    <!-- Report Header -->
    <div class="text-center mb-4">
      <img src="irhythm.png" alt="iRhythm" class="img-fluid mb-3" style="max-height:80px;">
      <h1 class="h3">ZioSuite Mobile Test automation report</h1>
      <p class="text-muted">${testplan.date}</p>
      <a href="${repoUrl}" class="btn btn-outline-primary btn-sm">Repository</a>
    </div>

    <!-- Execution Summary -->
    <div class="card mb-4">
      <div class="card-header bg-primary text-white">Execution Summary</div>
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
              <td>
                <span class="badge bg-success">${testplan.passRate}% (${testplan.pass})</span>
              </td>
              <td>
                <span class="badge bg-danger">${testplan.failRate}% (${testplan.fail})</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Expand/Collapse All Buttons -->
    <div class="mb-3">
      <button id="expandAll" class="btn btn-sm btn-outline-secondary me-2">Expand All</button>
      <button id="collapseAll" class="btn btn-sm btn-outline-secondary">Collapse All</button>
    </div>

    <!-- Accordion of Test Cases -->
    <div class="accordion" id="testAccordion">
      <#list testplan.tests?values as test>
        <#assign idx = test?index>
        <div class="accordion-item">
          <h2 class="accordion-header" id="heading-${idx}">
            <button class="accordion-button collapsed"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#collapse-${idx}"
                    aria-expanded="false"
                    aria-controls="collapse-${idx}"
                    data-test-color="${test.color}">
              ${test.globalId} — ${test.name}
            </button>
          </h2>
          <div id="collapse-${idx}"
               class="accordion-collapse collapse"
               aria-labelledby="heading-${idx}"
               data-bs-parent="#testAccordion">
            <div class="accordion-body">
              <p><strong>Test Case Link:</strong> <a href="${test.url}" target="_blank">${test.globalId}</a></p>
              <p><strong>Description:</strong> ${test.description}</p>
              <table class="table table-bordered table-sm mb-0">
                <thead class="table-light">
                  <tr>
                    <th style="width:5%;">Step</th>
                    <th style="width:30%;">Action</th>
                    <th style="width:25%;">Expected</th>
                    <th style="width:25%;">Evidence</th>
                    <th style="width:15%;">Status</th>
                  </tr>
                </thead>
                <tbody>
                  <#list test.steps?values as step>
                    <tr>
                      <td>${step?index + 1}</td>
                      <td>${step.action}</td>
                      <td>${step.expected}</td>
                      <td>
                        <#list step.screenshots?values as ss>
                          <img src="screenshots/${ss.name}"
                               class="img-fluid mb-1 screenshot-thumb"
                               data-bs-toggle="modal"
                               data-bs-target="#imageModal"
                               data-bs-src="screenshots/${ss.name}" />
                        </#list>
                      </td>
                      <td>
                        <#if step.result == 'PASS'>
                          <span class="badge bg-success">PASS</span>
                        <#else>
                          <span class="badge bg-danger">FAIL</span>
                        </#if>
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

  <!-- Image Preview Modal -->
  <div class="modal fade" id="imageModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
      <div class="modal-content">
        <div class="modal-body p-0 text-center">
          <img id="modalImage" src="" class="img-fluid" />
        </div>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
  <script>
    document.addEventListener('DOMContentLoaded', function() {
      // Expand/Collapse All
      const panels = document.querySelectorAll('.accordion-collapse');
      document.getElementById('expandAll').addEventListener('click', () => {
        panels.forEach(el => bootstrap.Collapse.getOrCreateInstance(el).show());
      });
      document.getElementById('collapseAll').addEventListener('click', () => {
        panels.forEach(el => bootstrap.Collapse.getOrCreateInstance(el).hide());
      });
      // Modal preview
      const modalEl = document.getElementById('imageModal');
      modalEl.addEventListener('show.bs.modal', event => {
        const img = event.relatedTarget;
        document.getElementById('modalImage').src = img.getAttribute('data-bs-src');
      });
      // Accordion header coloring
      panels.forEach(panel => {
        panel.addEventListener('show.bs.collapse', () => {
          const btn = panel.previousElementSibling.querySelector('.accordion-button');
          const color = btn.getAttribute('data-test-color') || '';
          // if color corresponds to success green
          if (color.toLowerCase().includes('28a745')) {
            btn.classList.add('bg-danger-subtle', 'text-dark');
          } else {
            btn.classList.add('bg-success-subtle', 'text-dark');
          }
        });
        panel.addEventListener('hide.bs.collapse', () => {
          const btn = panel.previousElementSibling.querySelector('.accordion-button');
          btn.classList.remove('bg-success-subtle', 'bg-danger-subtle', 'text-dark');
        });
      });
    });
  </script>
</body>
</html>