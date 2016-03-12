var xhr = new XMLHttpRequest();

function SuggestionList() {}

SuggestionList.prototype.requestSuggestions = function (oAutoSuggestControl) {

    var aSuggestions = [];
    var sTextboxValue = oAutoSuggestControl.textbox.value;
    var request = "/eBay/suggest?q=" + encodeURI(sTextboxValue);
    xhr.open("GET", request);
    xhr.onreadystatechange = function() {
         if (xhr.readyState == 4) {
             var s = xhr.responseXML.getElementsByTagName('CompleteSuggestion');

             //search for matching suggestion
             for (var i=0; i < s.length; i++) {
                 var text = s[i].childNodes[0].getAttribute("data");
                 aSuggestions.push(text);
             }
         }
         //provide suggestions to the control
         oAutoSuggestControl.updateSuggestions(aSuggestions);
    }

    xhr.send(null);

};