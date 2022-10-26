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

const loadingShow = () => {
    let maskHeight = $(document).height();
    let maskWidth = window.document.body.clientWidth;

    let mask = "<div id = 'mask' style='position:absolute; z-index:100; background-color:#000000; left:0; top:0;'></div>";

    $('body').append(mask);

    $("#mask").css({
        'width' : maskWidth,
        'height' : maskHeight,
        'opacity' : '0.4'
    })

    $(".loading").show();
}

const loadingHide = () => {
    $("#mask").remove();
    $(".loading").hide();
}

// 증명서 관련 /////////////////////////////////////////////
const getDate = () => {
    let date = new Date();
    let hour = ("0" + date.getHours()).slice(-2);
    let minute = ("0" + date.getMinutes()).slice(-2);
    let second = ("0" + date.getSeconds()).slice(-2);
    return getToday() + ' ' + hour + ':' + minute + ':' + second;
}

const getToday = () => {
    //오늘날짜 YYYY-MM-DD 형식으로 return
    var date = new Date();
    var year = date.getFullYear();
    var month = ("0" + (1 + date.getMonth())).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);
    return year + "-" + month + "-" + day;
}
const setEngMM = (month) => {
    const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    return monthNames[month - 1]
}
const setMMYYYY = (str) => {
    //YYYY-MM-DD >> mmm. YYYY
    return setEngMM(str.substring(5, 7)) + '. ' + str.substring(0, 4);
}
const setMMDDYYYY = (str) => {
    //YYYY-MM-DD >> mmm. DD. YYYY
    return setEngMM(str.substring(5, 7)) + '. ' + str.substring(8, 10) + '. ' + str.substring(0, 4);
}
const setYYYYMMDD = (str) => {
    //YYYY-MM-DD >> YYYY. MM. DD.
    return str.substring(0,4) + '. ' + str.substring(5, 7) + '. ' + str.substring(8, 10) + '.';
}
const setYYMMDD = (str) => {
    //YYYY-MM-DD >> YYMMDD
    return str.substring(2, 4) + str.substring(5, 7) + str.substring(8, 10);
}
const setKorYYYYMMDD = (str) => {
    //YYYY-MM-DD >> YYYY년 MM월 DD일
    return str.substring(0, 4) + '년 ' + str.substring(5, 7)+ '월 ' + str.substring(8, 10) + '일';
}
const blindNumber = (str) => {
    //주민등록번호 뒷자리 *로 보이도록
    let temp = '';
    for(let i = 0; i < str.length; i++) {
        temp += '*';
    }
    temp = temp.substring(0, 7);
    return temp;
}
const getId = (parameter) => {
    return document.querySelector(parameter);
}

const getInnerText = (parameter) => {
    return document.querySelector(parameter).innerText;
}
//////////////////////////////////////////////////////////