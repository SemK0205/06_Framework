// console.log("ajax-main.js.loaded");

// HTML상 요소 얻어와서 변수에 저장
// 할 일 개수 관련 요소
const totalCount = document.querySelector("#totalcount");
const completeCount = document.querySelector("#completeCount");
const reloadBtn = document.querySelector("#reloadBtn");
// 할 일 추가 관련 요소
const todoTitle = document.querySelector("#todoTitle");
const todoContent = document.querySelector("#todoContent");
const addBtn = document.querySelector("#addBtn");
// 할 일 목록 조회 관련 요소
const tbody = document.querySelector("#tbody");
// 할 일 상세 조회 관련 요소
const popupLayer = document.querySelector("#popupLayer");
const popupTodoNo = document.querySelector("#popupTodoNo");
const popupTodoTitle = document.querySelector("#popupTodoTitle");
const popupComplete = document.querySelector("#popupComplete");
const popupRegDate = document.querySelector("#popupRegDate");
const popupTodoContent = document.querySelector("#popupTodoContent");
const popupClose = document.querySelector("#popupClose");
// 상세 조회 팝업레이어 관련 버튼 요소
const changeComplete = document.querySelector("#changeComplete");
const updateView = document.querySelector("#updateView");
const deleteBtn = document.querySelector("#deleteBtn");
// 수정 레이어 관련 요소
const updateLayer = document.querySelector("#updateLayer");
const updateTitle = document.querySelector("#updateTitle");
const updateContent = document.querySelector("#updateContent");
const updateBtn = document.querySelector("#updateBtn");
const updateCancel = document.querySelector("#updateCancel");

/*
 fetch() API
 비동기 요청을 수행하는 최신 Javascript API 중 하나.

 - Promise 는 비동기 작업의 결과를 처리하는 방법 중 하나
 -> 어떤 결과가 올지는 모르지만 반드시 결과를 보내주겠다는 약속
 -> 비동기 작업이 맞이할 완료 또는 실패와 그 결과값을 나타냄
 -> 비동기 작업이 완료되었을 때 실행할 콜백함수를 지정하고,
    해당 작업의 성공 또는 실패 여부를 처리할 수 있도록 함

Promise 객체는 세 가지 상태를 가짐
- Pending (대기 중) : 비동기 작업이 완료되지 않은 상태
- Fulfilled (이행됨) : 비동기 작업이 성공적으로 완료된 상태
- Rejected (거부됨) : 비동기 작업이 실패한 상태
 */

// 전체 Todo 개수 조회 및 출력하는 함수
function getTotalCount() { // 함수 정의

  // 비동기로 서버에 전체 Todo 개수를 조회하는 요청
  // fetch() API 코드 작성
  fetch("/ajax/totalCount") // 서버로 "/ajax/totalCount" 로 GET 요청
  // 첫번째 then (응답을 처리하는 역할)
  .then( response => { // 서버에서 응답을 받으면,
                      // 이 응답(response)을 텍스트 형식으로 벼환하는 콜백함수
    // 매개변수 response : 비동기 요청에 대한 응답이 담긴 객체
    console.log(response);
    // response.text() : 응답 데이터를 문자열/숫자 형태로 변환한 결과를 가지는
    //                   Promise 객체 반환    
    
    return response.text();
  })
  // 두 번째 then (첫번째에서 return (변환)된 데이터를 활용하는 역할)
  .then( result => { // 첫번째 콜백함수가 완료된 후 호출되는 콜백함수
                  // 변환된 텍스트 데이터(result)를 받아서
                  // 콘솔에 단순 출력
    // 매개변수 result : 첫번째 콜백함수에서 반환된 Promise 객체의 PromiseResult 값
    // == result 매개변수로 받아서 처리
    console.log(result); // 최종 결과값

    document.querySelector("#totalCount").innerText = result;
  })
}

function getCompleteCount() {
  
  fetch("/ajax/completeCount")
  .then( response => response.text())
  .then( result => {
    completeCount.innerText = result; 
  })
}

// 새로고침 버튼이 클릭 되었을 때
reloadBtn.addEventListener("click", () => {
  getTotalCount();
  getCompleteCount();
  selectTodoList();
});

// 할 일 추가 버튼 클릭 시 동작
addBtn.addEventListener("click", () => {

  if(todoTitle.value.trim().length === 0 || todoContent.value.trim().length === 0) {
    alert("제목이나 내용은 비어있을 수 없습니다!");
  }

  // POST 방식 fetch() 비동기 요청 보내기
  // - 요청 주소 : "/ajax/add"
  // - 데이터 전달방식 (method) : "POST"
  // - 전달 데이터 : todoTitle 값, todoContent 값
  // JS <-> Java
  // JSON (JavaScript Object Notation) : 데이터를 표현하는 문법
  // { "name" : "홍길동" ,"age" : 20, "skill" : ["javascript", "java"]}
  
  // todoTitle과 todoContent를 저장한 JS 객체
  const param = {
    // key : value
    "todoTitle" : todoTitle.value,
    "todoContent" : todoContent.value
  }

  fetch("/ajax/add", {
    // key : value
    method : "POST", // post 방식 요청
    headers : {"Content-type" : "application/json"}, // 요청 데이터의 형식을 JSON 으로 지정
    body : JSON.stringify(param) // param이라는 JS 객체를 JSON(String)으로 변환
  })
  .then( resp => resp.text() ) // 반환된 값을 text로 변환
  .then( result => {
    // 첫 번째 then에서 반환된 값을 result 에 저장
    console.log(result);

    if(result > 0){
      alert("추가 성공!!!");
      todoContent.value="";
      todoTitle.value="";
      
      // 할 일이 새롭게 추가되었으므로
      // -> 전체 Todo 개수 조회하는 함수 재호출
      getTotalCount();

      // -> 전체 Todo 목록 조회하는 함수 재호출 예정
      selectTodoList();
    } else {
      alert("추가 실패...");
    }
  });
});

// 비동기 할 일 전체 목록을 조회하는 함수
const selectTodoList = () => {

  fetch("/ajax/selectList")
  .then(resp => resp.json()) // 응답결과를 json으로 받음
  .then(todoList => {
    // 매개변수 todoList:
    // 첫 번째 then에서 resp.text() / resp.json() 했냐에 따라
    // 단순 텍스트이거나, JS Object 일 수 있음.

    // 만약 resp.text() 사용했다면 문자열(JSON이 그대로 노출)
    // -> JSON.parse() 이용하여 JS Object 타입으로 변환 가능.

    // JSON.parse(JSON 데이터) : string -> JS Object
    // - string 형태의 JSON 데이터를 JS Object 타입으로 변환

    // JSON.stringify(JS Object) : JS Object -> string
    // - JS Object 타입을 string 형태의 JSON 데이터로 변환

    console.log(todoList);

    // -------------------------------------------------------

    // 기존에 출력되어 있던 할 일 목록을 모두 비우기
    tbody.innerHTML = "";

    // tbody에 tr/td 요소를 생성해서 내용 추가
    for(let todo of todoList) { // 향상된 for 문
      
      // tr 태그 생성
      const tr = document.createElement("tr"); // <tr></tr>

      // JS 객체에 존재하는 key 모음 배열 생성
      const arr = ['todoNo', 'todoTitle', 'complete', 'regDate'];

      for (let key of arr) {
        const td = document.createElement("td");

        if(key === 'todoTitle'){
          // 제목인 경우
          const a = document.createElement("a"); // a태그 생성
          a.innerText = todo[key]; // todo['todoTitle']
          a.href = "/ajax/detail?todoNo=" + todo.todoNo;
          td.append(a);
          tr.append(td);

          // a태그 클릭 시 페이지 이동 막기(비동기 요청 사용)
          a.addEventListener("click", e => {
            e.preventDefault(); // 기본 이벤트 방지
            
            // 할 일 상세 조회 비동기 요청 함수 호출
            selectTodo(e.target.href);
          });

          continue;

        }
        // 제목이 아닌 경우
        td.innerText = todo[key]; // todo['todoNo']
        tr.append(td); // tr의 마지막 요소 현재 td 추가하기
      }

      // tbody 의 자식으로 tr 추가
      tbody.append(tr);

    }
  })
}

// 비동기로 할 일 상세 조회하는 함수
const selectTodo = (url) => {
  // url = "/ajax/detail?todoNo=1" 형태의 문자열

  // fetch요청 시 url 이용
  fetch(url)
  .then(resp => resp.json())
  .then(todo => {
    
    popupTodoNo.innerText = todo.todoNo;
    
    popupTodoTitle.innerText = todo.todoTitle;
    
    popupComplete.innerText = todo.complete;
    
    popupRegDate.innerText = todo.regDate;
    
    popupTodoContent.innerText = todo.todoContent;

    // popup layer 보이게 하기
    popupLayer.classList.remove("popup-hidden")
    
    // update layer 가 열려 있다면 숨기기
    updateLayer.classList.add("popup-hidden");
    
  });
  
  // 비동기로 팝업 완료 여부 변경
  changeComplete.addEventListener("click", () => {
    
    complete = (popupComplete.innerText === 'Y') ? 'N' : 'Y';
    
    fetch("/ajax/complete?todoNo="+popupTodoNo.innerText+"&complete="+complete)
    .then(resp => resp.text())
    .then(result => {
  
      if(result > 0){
        getCompleteCount();
        selectTodoList();
        popupComplete.innerText = complete;
      }
  
    });
  });
  
  // 비동기로 팝업 삭제
  deleteBtn.addEventListener("click", () => {

    // 취소 클릭 시 아무것도 안함(해당 함수 종료)
    if( !confirm("정말 삭제하시겠습니까?") ) return;

    // 삭제할 할 일 번호 얻어오기
    const todoNo = popupTodoNo.innerText;

    // 삭제 비동기 요청 (DELETE)
    fetch("/ajax/delete", {
      method : "DELETE", // delete 방식 요청 -> @DeleteMapping 처리
      headers : {"Content-Type" : "application/json"},
      body : todoNo // todoNo 단일 값 하나는 JSON 형태로 자동 변환되어 전달됨
      // body : JSON.stringify(todoNo)
      // -> 원래는 이렇게 명시하는게 옮음(엄격한 환경에서는 꼭 명시)
    })
    .then(resp => resp.text())
    .then(result => {

      if(result > 0) { // 삭제 성공
        alert("삭제되었습니다!");

        // 상세 조회 팝업 레이어 닫기
        popupLayer.classList.add("popup-hidden");

        // 전체, 완료된 할 일 개수 다시 조회
        selectTodoList();
        getTotalCount();
        getCompleteCount();
      } else { // 삭제 실패
        alert("삭제 실패...");
      }
    });
  });

  // 비동기로 팝업 수정
  updateView.addEventListener("click", () => {
    popupLayer.classList.add("popup-hidden");
    updateLayer.classList.remove("popup-hidden");

    updateTitle.value = popupTodoTitle.innerText;

    // HTML 화면에서 줄 바꿈이 <br>로 인식되고 있는데
    // textarea에서는 \n으로 바꿔줘야 실제 줄바꿈으로 인식되어 출력된다!
    updateContent.value = popupTodoContent.innerHTML.replaceAll("<br>", "\n");

    // 수정 레이어 -> 수정 버튼에 data-todo-no 속성 추가
    updateBtn.setAttribute("data-todo-no", popupTodoNo.innerText);
  });

  // 수정 레이어 -> 수정 버튼 클릭 시
  updateBtn.addEventListener("click", (e) => {

    // 서버로 전달해야하는 값을 JS 객체로 묶음
    const obj = {
      "todoNo" : e.target.dataset.todoNo,
      "todoTitle" : updateTitle.value,
      "todoContent" : updateContent.value
    };
    fetch("/ajax/update",{
      method: "put",
      headers : {"Content-Type" : "application/json"},
      body : JSON.stringify(obj)
    })
    .then(resp=>resp.text())
    .then(result => {

      if(result>0){
        alert("수정 성공!!");
        updateLayer.classList.add("popup-hidden");
        popupLayer.classList.remove("popup-hidden");
        
        popupTodoTitle.innerText = updateTitle.value;
        popupTodoContent.innerHTML = updateContent.value.replaceAll("\n", "<br>");
        selectTodoList();

        updateTitle.value = '';
        updateContent.value = '';
        updateBtn.removeAttribute("data-todo-no"); // 속성 제거
      }else{
        alert("실패..");
      }

    });

  });

  // 팝업 수정 취소 클릭 시 다시 상세조회로 돌아오기
  updateCancel.addEventListener("click", () => {
    updateLayer.classList.add("popup-hidden");
    popupLayer.classList.remove("popup-hidden");
  });

  // 팝업창 끄기
  popupClose.addEventListener("click", () => {
    popupLayer.classList.add("popup-hidden");
  });
}

selectTodoList();
getTotalCount();
getCompleteCount();