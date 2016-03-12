//--------------------------------------------------------------------
// Constructor and init
//--------------------------------------------------------------------
function AutoSuggestControl(oTextbox, oProvider) {
    this.layer = null;
    this.provider = oProvider;
    this.textbox = oTextbox;
    this.init();
    this.currentSuggestion = -1;
}

AutoSuggestControl.prototype.init = function () {
    var oThis = this;
    
    //keyboard controls for the dropdown suggestions
    this.textbox.onkeyup = function (oEvent) {
        if (!oEvent)
            oEvent = window.event;
        oThis.handleKeyUp(oEvent);
    };
    
    this.textbox.onkeydown = function (oEvent) {
        if (!oEvent)
            oEvent = window.event;
        oThis.handleKeyDown(oEvent);
    };
    
    this.textbox.onblur = function () {
        oThis.hideSuggestions();
    };
    
    this.createDropDown();
}

//--------------------------------------------------------------------
// Functions for modifying elements
//--------------------------------------------------------------------
AutoSuggestControl.prototype.updateSuggestions = function(aSuggestions){
    if (aSuggestions.length > 0){
        var oDiv = null;
        this.layer.innerHTML = "";
        
        for (var i=0; i < aSuggestions.length; i++) {
            oDiv = document.createElement("div");
            oDiv.appendChild(document.createTextNode(aSuggestions[i]));
            this.layer.appendChild(oDiv);
        }
        
        this.layer.style.left = this.getLeft() + "px";
        this.layer.style.top = (this.getTop()+this.textbox.offsetHeight) + "px";
        this.layer.style.visibility = "visible";
    }
    else
        this.hideSuggestions();
}

AutoSuggestControl.prototype.show

AutoSuggestControl.prototype.selectRange = function (iStart, iLength) {
    if (this.textbox.createTextRange) {
        var oRange = this.textbox.createTextRange();
        oRange.moveStart("character", iStart);
        oRange.moveEnd("character", iLength - this.textbox.value.length);
        oRange.select();
    } else if (this.textbox.setSelectionRange) {
        this.textbox.setSelectionRange(iStart, iLength);
    }

    this.textbox.focus();
};

AutoSuggestControl.prototype.hideSuggestions = function () {
    this.layer.style.visibility = "hidden";
};

AutoSuggestControl.prototype.highlightSuggestion = function (oSuggestionNode) {

    for (var i=0; i < this.layer.childNodes.length; i++) {
        var oNode = this.layer.childNodes[i];
        if (oNode == oSuggestionNode) {
            oNode.className = "current"
        } else if (oNode.className == "current") {
            oNode.className = "";
        }
    }
};

AutoSuggestControl.prototype.createDropDown = function () {

    var oThis = this;

    //create the layer and assign styles
    this.layer = document.createElement("div");
    this.layer.className = "suggestions";
    this.layer.style.visibility = "hidden";
    this.layer.style.width = this.textbox.offsetWidth;
    
    //when the user clicks on the a suggestion, place it into the main search
    this.layer.onmousedown = 
    this.layer.onmouseup = 
    this.layer.onmouseover = function (oEvent) {
        oEvent = oEvent || window.event;
        oTarget = oEvent.target || oEvent.srcElement;
        if (oEvent.type == "mousedown") {
            oThis.textbox.value = oTarget.firstChild.nodeValue;
            oThis.hideSuggestions();
        } else if (oEvent.type == "mouseover") {
            oThis.highlightSuggestion(oTarget);
        } else {
            oThis.textbox.focus();
        }
    };
    document.body.appendChild(this.layer);
};

AutoSuggestControl.prototype.moveCurrentSuggestion = function (iMove) {
    var cSuggestionNodes = this.layer.childNodes;
    
    if (cSuggestionNodes.length > 0){
        if (iMove > 0 && this.currentSuggestion < cSuggestionNodes.length - iMove){
            oNode = cSuggestionNodes[++this.currentSuggestion];
            this.highlightSuggestion(oNode);
            this.textbox.value = oNode.firstChild.nodeValue;
        }
        else if (iMove < 0 && this.currentSuggestion > -1 + iMove){
            oNode = cSuggestionNodes[--this.currentSuggestion];
            this.highlightSuggestion(oNode);
            this.textbox.value = oNode.firstChild.nodeValue;
        }

    }
}

//--------------------------------------------------------------------
// Event handlers for keyboard actions
//--------------------------------------------------------------------
AutoSuggestControl.prototype.handleKeyUp = function (oEvent) {

    var iKeyCode = oEvent.keyCode;
    //make sure not to interfere with non-character keys
    if (iKeyCode < 32 || (iKeyCode >= 33 && iKeyCode < 46) || (iKeyCode >= 112 && iKeyCode <= 123)) {} 
    else {
        this.provider.requestSuggestions(this);
    }
};

AutoSuggestControl.prototype.handleKeyDown = function (oEvent) {
    var iKeyCode = oEvent.keyCode;
    if (iKeyCode == 38) //up arrow
        this.moveCurrentSuggestion(-1);
    if (iKeyCode == 40) //down arrow
        this.moveCurrentSuggestion(1);
};

//--------------------------------------------------------------------
// Helper functions to find element position
//--------------------------------------------------------------------
AutoSuggestControl.prototype.getLeft = function (){

    var oNode = this.textbox;
    var iLeft = 0;
    
    while(oNode.tagName != "BODY") {
        iLeft += oNode.offsetLeft;
        oNode = oNode.offsetParent;        
    }
    
    return iLeft;
};

AutoSuggestControl.prototype.getTop = function (){

    var oNode = this.textbox;
    var iTop = 0;
    
    while(oNode.tagName != "BODY") {
        iTop += oNode.offsetTop;
        oNode = oNode.offsetParent;
    }
    
    return iTop;
};
