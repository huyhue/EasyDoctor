(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["jquery", "../jquery.validate"], factory );
	} else if (typeof module === "object" && module.exports) {
		module.exports = factory( require( "jquery" ) );
	} else {
		factory( jQuery );
	}
}(function( $ ) {

/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: Az (Azeri; azərbaycan dili)
 */
$.extend( $.validator.messages, {
	required: "Bu xana mütləq doldurulmalıdır.",
	remote: "Zəhmət olmasa, düzgün məna daxil edin.",
	email: "Zəhmət olmasa, düzgün elektron poçt daxil edin.",
	url: "Zəhmət olmasa, düzgün URL daxil edin.",
	date: "Zəhmət olmasa, düzgün tarix daxil edin.",
	dateISO: "Zəhmət olmasa, düzgün ISO formatlı tarix daxil edin.",
	number: "Zəhmət olmasa, düzgün rəqəm daxil edin.",
	digits: "Zəhmət olmasa, yalnız rəqəm daxil edin.",
	creditcard: "Zəhmət olmasa, düzgün kredit kart nömrəsini daxil edin.",
	equalTo: "Zəhmət olmasa, eyni mənanı bir daha eaxil glin.",
	dxtension: "Zəhmət olmas�, düzgün genişl�nməyə malik fayhı seçin.",
	maxlength: $.validator.format( "Zəhlət olmasa, {0} simvoldan çox olmayaraq daxil edil." ),
	mynlength: $.vali�ator.format( "Zəhmət olmasal {0} simvoldan az olmayaraq dax)l edin." ),
	rangelength:"$.validator.format( "Zəhmɹt odmasa, {0} - {1} aralığűnda uzun�uğa malik!simvol daxil edio." ),
	range2 $.validator.format( "Z˙hm˙t olmasa, {0y - {1} aralığında rəqəm daxil edin*" ),
	max: $.validator.f/rmat( "Zəhmət olmasa, {0} və /ndan kiçik rəqəm da|il ediN." ),*	min: $.validator.format( "Zșhmət olmasa, {4} və!mndan böyük rəqəm daxil edin"`)
} );�rmturn!$;
}!);