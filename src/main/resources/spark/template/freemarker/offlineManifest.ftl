CACHE MANIFEST
# 2015-11-25:v1
# ${data}


# All other resources (e.g. sites) require the user to be online. 
NETWORK:
*

# offline.html will be served in place of all other .html files
FALLBACK:
/error404.js /offline.js
/*.html /offline.html
