<#import "base.ftl" as layout>

<@layout.page title="Home">
  <h1>User View</h1>

  <h3>User: ${name}</h3>
  <ul>
      <#list roles as role>
        <li>${role}</li>
      </#list>
  </ul>
</@layout.page>
