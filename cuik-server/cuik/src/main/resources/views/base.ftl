<#macro page title>
  <!DOCTYPE html>
  <html>
  <head>
    <title>${title}</title>
  </head>
  <body>
    <header>My Header</header>

    <main>
      <#nested>
    </main>

    <footer>My Footer</footer>
  </body>
  </html>
</#macro>
