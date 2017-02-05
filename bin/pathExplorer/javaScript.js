var previousEvent = null;
var previousElemetOutline = null;
var enableIntercept;
var highlightColor;

document.addEventListener('click', intercept, true);
document.addEventListener('onclick', intercept, true);
document.addEventListener('ondblclick', intercept, true);



function intercept(event) {

	window.alert(event);
	
	if (previousEvent != null) {
		previousEvent.target.style.outline = previousElemetOutline;
	}
	previousEvent = event;
	previousElemetOutLine = event.target.style.outline;
	event.target.style.outline = 'solid ' + highlightColor + ' 5px';
	var evt = event ? event : window.event;
	var path = getDomPath(event.target);
	app.sendMsg(path);	
	
	if (enableIntercept == true) {
		event.preventDefault();
		event.stopPropagation();
	}
}

function getDomPath(element) {
	var stack = [];
	while (element.parentNode != null) {
		var sibCount = 0;
		var sibIndex = 0;

		for (var i = 0; i < element.parentNode.childNodes.length; i++) {
			var sib = element.parentNode.childNodes[i];
			if (sib.nodeName == element.nodeName) {
				if (sib === element) {
					sibIndex = sibCount;
				}
				sibCount++;
			}
		}

		if (element.hasAttribute('id') && element.id != '') {
			stack.unshift(element.nodeName.toLowerCase() + '#' + element.id);
		} else if (sibCount > 1 && element.className != '') {
			stack.unshift(element.nodeName.toLowerCase() + '.'
					+ element.className);
		} else if (sibCount > 1) {
			stack.unshift(element.nodeName.toLowerCase() + ':nth-child('
					+ (sibIndex + 1) + ')');
		} else {
			stack.unshift(element.nodeName.toLowerCase());
		}

		element = element.parentNode;
	}
	return stack.slice(1);
}
