/* 듀얼 모니터 윈도우 팝업 가운데 정렬 */
const popupCenter = (url, title, w, h, opts) => {
    let _innerOpts = '';
   if(opts !== null && typeof opts === 'object' ){
       for (let p in opts ) {
           if (opts.hasOwnProperty(p)) {
               _innerOpts += p + '=' + opts[p] + ',';
           }
       }
   }
   let dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
   let dualScreenTop = window.screenTop != undefined ? window.screenTop : screen.top;

   let width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
   let height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

   let left = (((width / 2) - (w / 2)) + dualScreenLeft) - 100;
   let top = ((height / 2) - (h / 2)) + dualScreenTop;
   let newWindow = window.open(url, title, _innerOpts + ' width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);

   if (window.focus) {
       newWindow.focus();
   }
}

const getValue = (parameter) => {
    return document.querySelector(parameter).value;
}

const setValue = (name, val) => {
    document.querySelector(name).value = val;
}