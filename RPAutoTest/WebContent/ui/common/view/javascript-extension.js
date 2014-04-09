/**
 * 
 */
function includeJS(filename)
{
    var head = document.getElementsByTagName('head')[0];

    var script = document.createElement('script');
    script.src = filename;
    script.type = 'text/javascript';

    head.appendChild(script);
}

function includeCSS(filename)
{
    var head = document.getElementsByTagName('head')[0];

    var style = document.createElement('link');
    style.href = filename;
    style.rel = 'stylesheet';
    style.type = 'text/css';

    head.appendChild(style);
}