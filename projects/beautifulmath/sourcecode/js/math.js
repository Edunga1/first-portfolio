var canvas;					// 캔버스 dom
var ctx;					// context
var canvasWidth;			// 캔버스 너비
var canvasHeight;			// 캔버스 높이
var handlers = [];			// 타임아웃 핸들러

var often = 5;				// 빈도수
var delay = 100;			// 반복 딜레이

var circleR = 300;			// 원 반경
var rotateRadius = 15;		// 회전각도
var color1 = "#6e6e6e";		// 일반 선 색상
var color2 = "#c8c8c8";		// 강조 선 색상
var color3 = "#ffffff";		// 매우 강조 선 색상
var isRandomColor = false;	// 랜덤 색상 여부
var bgcolor = "#000000";	// 배경 색상
var isTransparentBg = false;// 배경 투명 여부

/*
*	캔버스 초기화
*	캔버스의 객체와 정보를 받아온다.
*/
function canvasInit(){

	canvas = document.getElementById("canvas");		// 캔버스 DOM
	canvas.width = screen.width * 0.85;				// 캔버스 크기를 변경함
	canvas.height = 500;							// css에서 직접 변경시 canvas 내용들이 확대되버리므로 주의
	canvasWidth = screen.width * 0.85;
	canvasHeight = 500;
	ctx = canvas.getContext("2d");					// 컨텍스트 획득
}

/*
*	입력값을 불러옴
*/
function adjustVariables(){
	
	var often_n = document.getElementById("mOften").value;	// 빈도수
	if( often_n % 1 === 0 ) often = +often_n;
	var delay_n = document.getElementById("mDelay").value;	// 그리기속도
	if( delay_n % 1 === 0 ) delay = +delay_n;
	var radius_n = document.getElementById("mRadius").value;// 반경
	if( radius_n % 1 === 0 ) circleR = +radius_n;
	
	rotateRadius = document.getElementById("mRotateRadius").value;	// 회전각도
	color1 = document.getElementById("mColor1").value;	// 일반 선 색상
	color2 = document.getElementById("mColor2").value;	// 강조 선
	color3 = document.getElementById("mColor3").value;	// 매우 강조
	isRandomColor = document.getElementById("mRandomColor").checked;// 랜덤 색상 여부
	bgcolor = document.getElementById("mBgcolor").value;	// 배경 색상
	isTransparentBg = document.getElementById("mTranspBg").checked;	// 배경 투명 여부
}

/*
*	선 그리기 예약
*/
function drawLines(){

	canvasClear();		// 캔버스 클리어
	adjustVariables();	// 변수 조정
	
	// 배경색 설정 여부 결정
	if( !isTransparentBg ){							// 배경을 투명하게 하지 않는다면
		ctx.fillStyle = bgcolor;					// 배경 색상 설정
		ctx.fillRect(0, 0, canvasWidth, canvasHeight);
	}
	
	// 시작점을 중심으로 이동함
	ctx.translate(canvasWidth/2,canvasHeight/2);
	
	// setTimout을 통해 drawTimeout 함수 호출
	for (var i = 0, id = 0; i < 25; i++) {
		for (var a = -45; a <= 45; a+=often) {
			handlers[id++] = setTimeout("drawTimeout("+a+");",delay * id);
		}
	}
}

/*
*	setTimeout 콜백함수, 실제로 선을 그림
*/
function drawTimeout(a){
	ctx.beginPath();
	ctx.moveTo(0,circleR);
	var radians = Math.PI/180*a;
	var x = (circleR * Math.sin(radians)) / Math.sin(Math.PI/2 - radians);
	ctx.lineTo(x,0);

	// 선 두께 및 색상 설정
	if (Math.abs(a) == 45) {
		ctx.strokeStyle = isRandomColor ? getRandomColor() : color3;
		ctx.lineWidth=1;
	} else if (a == 0) {
		ctx.strokeStyle = isRandomColor ? getRandomColor() : color2;
		ctx.lineWidth=0.5;
	} else {
		ctx.strokeStyle = isRandomColor ? getRandomColor() : color1;
		ctx.lineWidth=0.2;
	}
	
	ctx.stroke();
	ctx.rotate((Math.PI/180)*rotateRadius);
}

/*
*	랜덤 색상 코드 반환
*/
function getRandomColor(){
	return '#' + Math.floor(Math.random()*16777215).toString(16);
}

/*
*	캔버스 저장 및 업로드
*/
function canvasSave(){
	
	// 캔버스 이미지를 데이터 URL로 변환
	var imgDataURL = canvas.toDataURL("image/png");
	
	// 만약 데이터크기가 너무 적으면 취소
	if( imgDataURL.length < 30000 ){
		alert("캔버스가 비어있어요!");
		return false;
	}
	
	// AJAX 저장 요청
	var ajax = new XMLHttpRequest();
	ajax.open("POST",'imageSave.php?filename='+getCondition(),false);
	ajax.setRequestHeader('Content-Type', 'application/upload');
	ajax.onreadystatechange = function() {
	
		// 요청 완료 후 응답 준비 && 요청이 정상 처리되었을 때
		if(ajax.readyState == 4 && ajax.status == 200){
			alert("캔버스 이미지가 업로드 되었습니다!");
		}
	}
	ajax.send(imgDataURL);
}

/*
*	캔버스 클리어
*/
function canvasClear(){

	// 모든 setTimeout 멈춤
	for(var i=0; i<handlers.length; i++)
		clearTimeout(handlers[i]);					// 이전 setTimout 제거
	handlers = [];									// 핸들러 초기화
	
	// 캔버스 설정 초기화 및 지움
	ctx.setTransform(1, 0, 0, 1, 0, 0);				// 초기화
	ctx.clearRect(0, 0, canvasWidth, canvasHeight);	// 클리어
}

/*
*	현재 상태 조정값 문자열로 반환
*/
function getCondition(){

	var result =
		document.getElementById('mRadius').value+'_'+
		document.getElementById('mOften').value+'_'+
		document.getElementById('mDelay').value+'_'+
		document.getElementById("mRotateRadius").value+'_'+
		document.getElementById('mColor1').value+'_'+
		document.getElementById('mColor2').value+'_'+
		document.getElementById('mColor3').value+'_'+
		document.getElementById('mRandomColor').checked+'_'+
		document.getElementById('mBgcolor').value+'_'+
		document.getElementById('mTranspBg').checked;
	return result.replace(/#/g, '');
}

/*
*	문자열을 현재 상태로 변환
*/
function setCondition(str){

	var i=0;
	var cons = str.replace('.png','').split('_');	// 문자열 자름
	document.getElementById('mRadius').value = cons[i++];
	document.getElementById('mOften').value = cons[i++];
	document.getElementById('mDelay').value = cons[i++];
	document.getElementById("mRotateRadius").value = cons[i++];
	document.getElementById('mColor1').value = '#'+cons[i++];
	document.getElementById('mColor2').value = '#'+cons[i++];
	document.getElementById('mColor3').value = '#'+cons[i++];
	document.getElementById('mRandomColor').checked = (cons[i++] === 'true');
	document.getElementById('mBgcolor').value = '#'+cons[i++];
	document.getElementById('mTranspBg').checked = (cons[i++] === 'true');
}
