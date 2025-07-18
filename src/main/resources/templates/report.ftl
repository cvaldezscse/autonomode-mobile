<!DOCTYPE html>
<html>
<head>
<title>${markupTitle}</title>
<style type="text/css">
        table
        {
            table-layout:fixed;
            width:100%;
            word-break: break-word;
        }
    </style>
  </head>
  <body bgcolor="20b2aa">
  <div style="background-color: #20b2aa">
      <!--  ***************************************************************** -->
      <!--                             Report Header                          -->
      <!--  ***************************************************************** -->
      <table BORDER="1" bordercolor="FDFEFE" style="width:100%; height: 25%" align="center" bgcolor="20b2aa">
          <tr>
               <th colspan="100%" align="center"><img src="irhythm.png" alt="iRhythm"/></th>
          </tr>
          <tr>
               <th colspan="25%">ZioSuite Mobile App Test Report</th>
               <th colspan="25%">Automation Report</th>
               <th colspan="25%"><a href="${repoUrl}">Repository</a></th>
               <th colspan="25%">${testplan.date}</th>
          </tr>
          <!-- <tr style="background-color: #008080; color: white; text-align: center;">
              <th colspan="100%">
                  <span style="font-weight: bold;">Execution Time:</span>
                  <span style="font-weight: normal;">27 Seconds, 348 Milliseconds</span>
              </th>
          </tr> -->
          <tr>
               <th colspan="100%">Execution Summary</th>
          </tr>
          <tr>
              <th colspan="10%">Platform</th>
              <th colspan="15%">Environment</th>
              <th colspan="15%">App Version</th>
              <th colspan="20%">Time Elapsed</th>
              <th colspan="10%">Completed Test Count</th>
              <th colspan="15%">Pass Rate</th>
              <th colspan="15%">Fail Rate</th>
          </tr>
          <tr>
              <td colspan="10%" align="center">${platform}</td>
              <td colspan="15%" align="center">${environment}</td>
              <td colspan="15%" align="center">${appVersionPlusBuild}</td>
              <td colspan="20%" align="center">${testplan.executionTime}</td>
              <td colspan="10%" align="center">${testplan.tests?size}</td>
              <td colspan="15%" align="center" style="color: #1B5E20; font-weight: bold;">${testplan.passRate}% (${testplan.pass})</td>
              <td colspan="15%" align="center" style="color: #B71C1C; font-weight: bold;">${testplan.failRate}% (${testplan.fail})</td>
          </tr>
          </table>

      <!--  ***************************************************************** -->
      <!--                              Test Results                          -->
      <!--  ***************************************************************** -->
      <table BORDER="1" bordercolor="20b2aa" bgcolor="FDFEFE" style="width:100%; height: 100%" align="left">
     <#list testplan.tests?keys as key>
     <#assign test = testplan.tests[key]/>
         <table BORDER="1" bordercolor="20b2aa" bgcolor="FDFEFE" style="width:100%" align="left">
           <tr>
              <th colspan="100%" align="center" height="25" bgcolor="${test.color}">
                <span style="font-weight: bold;">Test Case Id:</span> <span style="font-weight: normal;">${test.globalId}&nbsp;->&nbsp;<a href="${test.url}" target="_blank">[Test case link]</a></span><br>
                <span style="font-weight: bold;">Test Case Name:</span> <span style="font-weight: normal;">${test.name}</span><br>
                <span style="font-weight: bold;">Test Case Description:</span> <span style="font-weight: normal;">${test.description}</span>
              </th>
           </tr>
           <tr>
             <th colspan="5%" height="25" bgcolor="#909090"> # Step </th>
             <th colspan="30%" height="25" bgcolor="#909090"> Action </th>
             <th colspan="30%" height="25" bgcolor="#909090"> Expected </th>
             <th colspan="30%" height="25" bgcolor="#909090"> Evidences </th>
             <th colspan="5%" height="25" bgcolor="#909090"> Status </th>
           </tr>
           <#list test.steps?keys as stepKey>
           <#assign testStep = test.steps[stepKey]/>
              <tr>
                <td colspan="5%" height="400" align="center"bgcolor="${testStep.color}">${stepKey}</td>
                <td colspan="30%" height="400"> ${testStep.action} </td>
                <td colspan="30%" height="400"> ${testStep.expected} </td>
                <td colspan="30%" height="400">
                  <#list testStep.logs?keys as logKey>
                  <#assign log = testStep.logs[logKey]/>
                    ${log}<br>
                  </#list>
                  <#list testStep.screenshots?keys as screenshotKey>
                  <#assign screenshot = testStep.screenshots[screenshotKey]/>
                    <center><img src="screenshots/${screenshot.name}" height="100%" width="60%"/></center><br>
                  </#list>
                </td>
                <td colspan="5%" height="400" align="center" bgcolor="${testStep.color}"> <strong>${testStep.result}</strong> </td>
              </tr>
            </#list>
         </table>
     </#list>
     <br><br>
     </table>
    </div>
    <br><br>
  </body>
</html>