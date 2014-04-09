includeCSS("ui/common/res/Header.css");

function Header(p_title) {
	var $headerDiv = $("<div class='header'></div>");
	var $homeSpan = $("<span class='home'>HOME</span>");
	var $titleSpan = $("<span class='title'>" + p_title + "</span>");
	$headerDiv.append($homeSpan);
	$headerDiv.append($titleSpan);
	return $headerDiv;
};