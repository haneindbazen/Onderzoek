/**
 * General functionalities
 */

function insertMeldingInfo(melding){
	var $meldingDiv =  $('#iframe').contents().find("#s-melding").children();
	
	while ($meldingDiv.length) {
		$meldingDiv = $meldingDiv.children();
	}
	
	$meldingDiv.end().val(melding);
	$meldingDiv.end().text(melding);
	
}