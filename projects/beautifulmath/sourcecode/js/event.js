

/*
*	문서 초기화
*/
function eventInitialize(){
	
	// 스크롤바 움직임 이벤트 콜백함수 설정
	$(window).scroll(menuFollowsScroll);
	menuFollowsScroll();	// 처음 한 번 실행
}

/*
*	조절창을 보이거나 감춤
*/
function toggleAdjuster(){
	
	var adjuster = $('#adjuster');		// 조절창
	adjuster.animate({width: 'toggle'});// 슬라이드 애니메이션을 통해 보이거나 감춤
}

/*
*	조절창과 업로드창을 스크롤바 위치로 이동
*/
function menuFollowsScroll(){

	var MARGIN = 50;	// 조절창 상단 여백
	
	var adjustWrapper = $('#wrapAdjust');	// 조절창 
	var uploadWrapper = $('#btnUploadList');// 업로드창
	var topPosition = $(window).scrollTop();// 스크롤바 위치
	
	// 이동
	adjustWrapper.stop().animate({'top': MARGIN + 70 + topPosition + 'px'}, 1000);
	uploadWrapper.stop().animate({'top': MARGIN + topPosition + 'px'}, 1000);
}

/*
*	업로드 목록 불러오기
*/
function loadUploadImgs(){
	
	var imgWrapper = $('#wrapUploadList');	// 업로드 목록 묶음
	var imgInner = $('#innerList');			// 이미지 목록
	
	var dir = "upload/";	// 저장 폴더
	var imgExt = ".png";	// 얻고자 하는 이미지 확장자
	
	// 이전 목록 비움
	imgInner.empty();
	
	// 이미지 목록 보이기
	imgWrapper.show();
	$('#curtain').show();
	
	// 저장 폴더에 대해서 AJAX 요청
	$.ajax({
		url: dir,
		// 성공적으로 요청완료 시 콜백함수
		success: function(data) {
			// 모든 이미지 파일들을 찾아서 불러옴
			$(data).find('a:contains(' + imgExt + ')').each( function(){	// 이미지 확장자를 포함하는 태그를 모두 찾음
				var filename = this.href.match(/[^\/]*$/g)[0];					// 이미지 주소
				var imgTag = $('<img />',{
					src: dir + filename,	// 이미지 주소
					click: function(e){		// 클릭 이벤트
						setCondition(filename);	// math.js 세팅 불러오기
						hideUploadList();		// 업로드창 감추기
						drawLines();			// math.js 선 그리기
					}
				});
				imgInner.append(imgTag);	// 이미지 추가
			});// end each
		}// end success
	});// end ajax
}

/*
*	업로드 목록 감추기
*/
function hideUploadList(){

	$('#wrapUploadList').hide();
	$('#curtain').hide();
}