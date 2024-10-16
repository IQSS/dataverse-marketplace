# dataverse-marketplace

For MVP:<br/><br/>
• Focus just on external tools<br/>
• Specifically remote external tools<br/>
• This encompasses the vast majority of external tools currently in production<br/>
• Simpler, as no need to install any apps, just register the manifest file<br/>
• Would not be tied to a specific version of the app (though we could ask apps, for example, to provide an API which we could query to know existing versions?<br/>
•Simple db would contain all the tool info to “advertise” and register (e.g. the manifest file)<br/>
• Simple UI to add tools to that DB<br/>
• Locally run apps are more complicated, so not for MVP<br/>
• Marketplace itself would be an external tool, that eventually could be embedded into Dataverse SPA UI (under some admin tool type section, maybe?)<br/>
• Requires modification of external tools API to be runnable outside of Admin (can be created as an issue already)<br/>
• Need to think about auth / security - one time URL for this?<br/>
• Provide extra auth specifically at manifest registration time<br/>
• API for listing public info of tools<br/>
• Define tool compatibility with which version of Dataverse<br/>
• Check if tool is still working / status check<br/>
• Start as a shiny app<br/>
