function Tracker() {

    this.getDataText = getDataText;
    this.getDateTime = getDateTime;
    this.getEscapedUrl = getEscapedUrl;
    this.getEscapedDateTime = getEscapedDateTime; 
    
    function getDataText(){
        var escapeWrapper = window.encodeURIComponent || escape;
        var dataText = "url=" + escapeWrapper(document.location.href)
        +"&datetime=" + escapeWrapper(this.getDateTime());
        return dataText;
    }
    
    function getEscapedUrl() { 
    	return escapeWrapper(document.location.href);
    }

    function getEscapedDateTime() {
    	return escapeWrapper(this.getDateTime());
    }
    
    function getDateTime() {
        var now = new Date();
        var dateTime = now.getUTCFullYear()   + '-' +
        pad(now.getUTCMonth() + 1) + '-' +
        pad(now.getUTCDate())     + 'T' +
        pad(now.getUTCHours())     + ':' +
        pad(now.getUTCMinutes())   + ':' +
        pad(now.getUTCSeconds())   + 'Z';
        return dateTime;
    }

    function pad(n) {
        return n < 10 ? '0' + n : n;
    }
}


