<div align=center>
  <img width="300" alt="스크린샷 2022-09-30 오후 5 31 24" src="https://github.com/woowacourse-teams/2023-stamp-crush/assets/62367797/3779aae1-6cc1-4843-94cc-a5fd949e0a06">
  <h2> 흩어진 쿠폰 한번에 관리하자! </h2>
<br>
<a href="https://www.stampcrush.site/">서비스 소개</a>
<br>
<a href="https://www.stampcrush.site/admin">사장 모드</a>
<br>
<a href="https://www.stampcrush.site/counpon-list">고객 모드</a>
  <br>
  <br>
  <strong>스탬프크러쉬</strong>는 온라인 쿠폰 적립 및 관리 플랫폼으로
  <br>
  카페 사장님에게 개성 있는 쿠폰 제작과 고객에게 간편한 적립을 제공해주는 서비스입니다.
  <br>
  <br>

[서비스 소개글 바로가기](https://github.com/woowacourse-teams/2023-stamp-crush/wiki)

[서비스 소개 페이지 바로가기](https://sites.google.com/woowahan.com/woowacourse-demo-5th/프로젝트/스탬프크러쉬)

</div>

<div align=left>

# 주요 기능 소개

## 쿠폰 커스텀 기능(사장 모드)

### 쿠폰 제작 방식 선택( 템플릿 / 커스텀 )

  <table>
    <tr>
      <img src="docs/template-or-custom.gif" width="600" alt="select between template and custom"/>
    </tr>
  </table>
    <br/>
    <br/>

  <table>
    <tr>
      <td>
      <h3>템플릿</h3>
        <p>스탬프크러쉬가 <strong>제공하는 템플릿을 이용</strong>하여 쿠폰을 디자인할 수 있습니다.</p>
      </td>
      <td><img src="docs/make-template.gif" width="600" alt="make template coupon"/></td>
    </tr>
    <tr>
      <td>
        <h3>커스텀</h3>
        <p>사용자가 <strong>직접 업로드한 파일을 이용</strong>하여 쿠폰 디자인을 커스터마이징할 수 있습니다.</p>
      </td>
      <td><img src="docs/image-upload.gif" width="600" alt="coupon image upload"/></td>
    </tr>
  </table>

  <br/>

### 스탬프 위치 커스터마이징

  <table>
    <tr>
      <td>쿠폰에 찍힐 <strong>스탬프 좌표와 순서를 커스터마이징</strong>하고 S3에 이미지를 업로드합니다.</td>
    </tr>
    <tr>
      <td><img src="docs/coupon-custom.gif" width="800" alt="coupon custom"/></td>
    </tr>
  </table>

  <br/>
  <br/>

## 스탬프 적립(사장 모드)

  <table>
    <tr>
      <td>
        <h3>DB 미보유 전화번호</h3> 
        <p>첫 방문 고객 확인 / 전화번호 입력 실수 방지를 위해 DB에 존재하지 않는 번호 입력 시 알림을 띄웁니다.</p></td>
      <td>
        <h3>DB 보유 전화번호</h3> 
        <p>입력받은 전화번호로 회원/비회원, 쿠폰 보유 여부, 스탬프 개수, 쿠폰 디자인 등 고객의 정보를 요청/응답합니다.</p>
      </td>
    </tr>
    <tr>
      <td><img src="docs/earn-first.gif"/></td>
      <td><img src="docs/earn-twice.gif"></td>
    <tr>
  </table>

  <table>
    <tr>
      <td><strong>쿠폰을 커스터마이징한 경우 사용자가 설정한 스탬프 위치와 순서에 맞게</strong> 스탬프가 찍힙니다.</td>
    </tr>
    <tr>
      <td><img src="docs/earn-stamp-1.gif" width="800" alt="earn stamp"/></td>
    </tr>
  </table>

  <table>
    <tr>
      <td>적립하고자 하는 스탬프의 개수가 쿠폰 최대 스탬프 개수를 넘어가면 새 쿠폰을 발급하여 스탬프를 적립합니다.</td>
    </tr>
    <tr>
      <td><img src="docs/earn-stamp-2.gif" width="800" alt="earn stamp"/></td>
    </tr>
  </table>

  <br/>
  <br/>

## 카페 관리(사장 모드)

### 카페 정보 입력

  <table>
    <tr>
      <td>
        <h3>[사장모드]</h3> 
        <p>카페 대표 이미지, 전화번호, 영업 시간, 소개글 등을 입력할 수 있습니다.</p></td>
      <td>
        <h3>[고객모드]</h3> 
        <p>쿠폰을 터치하여 카페의 정보를 확인할 수 있습니다.</p>
      </td>
    </tr>
    <tr>
      <td><img src="docs/cafe-info.gif"/></td>
      <td><img src="docs/coupon-info.gif"></td>
    <tr>
  </table>
  <br/>
  <br/>

### 고객 관리

  <table>
    <tr>
      <td>
        <h3>고객 목록 필터링</h3>
        <p>스탬프 크러쉬 서비스 가입(회원) / 미가입(임시), 최근 방문순, 리워드 순 등 필터링이 가능합니다.</p>
      </td>
    </tr>
    <tr>
      <td>
        <img src="docs/customer-filter.gif" width="800"/>
      </td>
    </tr>
  </table>

  <br/>
  <br/>
  <br/>

## 보유 쿠폰 조회 기능(고객 모드)

  <table>
    <tr>
      <td width="250">
        <h3>쿠폰 접기/펼치기</h3> 
        <p>다수의 쿠폰이 존재할 때 쿠폰을 모아서 볼 수도 있고, 펼쳐서 볼 수도 있습니다.</p>
      </td>
      <td width="250">
        <h3>즐겨찾기 등록/해제</h3> 
        <p>쿠폰 오른쪽 위의 별모양 아이콘을 눌러 카페 즐겨찾기 등록/해제할 수 있습니다.</p>
      </td>
      <td width="250">
        <h3>즐겨찾기 모아보기</h3> 
        <p>즐겨찾기 된 쿠폰만을 확인할 수 있습니다.</p>
      </td>
    </tr>
    <tr>
      <td><img src="docs/coupon-list.gif"/></td>
      <td><img src="docs/fav-toggle.gif"/></td>
      <td><img src="docs/fav-toggle-1.gif"/></td>
    <tr>
  </table>
  
  <br/>
  <br/>
  <br/>

</div>

---

## 팀원 소개

|                                                                                               Frontend                                                                                               |                                                                                                        Frontend                                                                                                         |                                                                                                       Frontend                                                                                                        |
| :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: |
|                                                          <img src="https://avatars.githubusercontent.com/u/62367797?v=4" alt="" width=200>                                                           |                                                                    <img src="https://avatars.githubusercontent.com/u/56749516?v=4" alt="" width=200>                                                                    |                                                                   <img src="https://avatars.githubusercontent.com/u/90092440?v=4" alt="" width=200>                                                                   |
|                                                                              [라잇](https://github.com/kangyeongmin) ✨                                                                              |                                                                                         [윤생](https://github.com/2yunseong) 🐿️                                                                                         |                                                                                       [레고](https://github.com/regularPark) 🦦                                                                                       |
|                                                                                          팀원들이 본 라잇은                                                                                          |                                                                                                   팀원들이 본 윤생은                                                                                                    |                                                                                                  팀원들이 본 레고는                                                                                                   |
| 🫀 책임감 있고, 팀에 주인의식을 가지는 기획자 <br/> ⭐️ 스탬프크러쉬의 비타민 <br/> 🤔 고객의 입장에서 한 번 더 생각해보는 비지니스 전문가 <br/> 👩🏻‍💻 언어 사용에 있어 기본기가 좋고 학습속도가 빨라요 | 👀 다양한 영역에 대한 이해력과 통찰력 <br/> 😎 맡은 바를 책임감 있게 해내고야 마는 멋쟁이 <br/> 🧑‍💻 새 지식에 대한 흡수력이 놀랄만큼 좋아요. 동시에 기술 도입에 있어서는 신중하고 합리적이에요 <br/> 📃 문서화하면 윤생! | 🧑🏻 우리팀 큰아빠. 밤, 낮, 주말 가리지 않고 성실하게 개발하는. 어려운 기능도 뚝딱뚝딱 <br/> 💪🏻 끈기있게 문제를 해결하려는 자세가 인상깊다 <br/> 👨🏻‍💻 요구사항 변동에 유연하게 대응할 수 있는 개발자 <br/> 🐛 버그 학살자 |

|                                                                                                   Backend                                                                                                    |                                                                                                    Backend                                                                                                     |                                                                                           Backend                                                                                           |                                                                                                       Backend                                                                                                       |
| :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: |
|                                                              <img src="https://avatars.githubusercontent.com/u/107979804?v=4" alt="" width=200>                                                              |                                    <img src="https://github.com/woowacourse-teams/2023-stamp-crush/assets/91937954/2be0f555-b276-4ebe-ae21-986d42b7e407" alt="" width=200>                                     |                                                      <img src="https://avatars.githubusercontent.com/u/62167801?v=4" alt="" width=200>                                                      |                                                                  <img src="https://avatars.githubusercontent.com/u/91937954?v=4" alt="" width=200>                                                                  |
|                                                                                   [깃짱](https://github.com/gitchannn) 🌟                                                                                    |                                                                                   [하디](https://github.com/jundonghyuk) 🌰                                                                                    |                                                                            [레오](https://github.com/youngh0) 🐆                                                                            |                                                                                        [제나](https://github.com/yenawee) 🌱                                                                                        |
|                                                                                              팀원들이 본 깃짱은                                                                                              |                                                                                               팀원들이 본 하디는                                                                                               |                                                                                     팀원들이 본 레오는                                                                                      |                                                                                                 팀원들이 본 제나는                                                                                                  |
| 😆 분위기 메이커 <br/> 👑 엣지케이스의 여왕. 생각지도 못한 부분까지 고려하는 경우가 많아서 놀랄 때가 많아요 <br/> ✒️ 스탬프크러쉬 회고 장인. 모든 일을 글로서 정리하는 능력이 탁월한. <br/> 🎉 아이디어 뱅크 | 🔫 문제 해결사 <br/> 🙂 상수(constant) 같은 느낌이에요. 감정 기복없이 항상 꾸준하게 잘해줘요 <br/> 🎤 복잡하고 장황한 회의를 지혜롭고 핵심있게 이끌어가는 mc <br/> 🧐 생각을 깊게 해서 코드 리뷰가 질이 좋아요 | 🥰️ 우리팀 인기쟁이 <br/> 🙌 백엔드 개발자로서 갖춰야 할 여러 스킬들을 두루두루 잘해요 <br/> 💡 반복적인 개발에 지칠 때 새로운 인사이트를 많이 가져오고, 여기저기서 본 지식을 잘 공유해줘요 | 🤝 프론트, 백엔드, 서비스 이용자 사이에서 의견을 부드럽게 조율하는 개발자 <br/> 🗓️ 팀원들이 놓친 부분을 소름돋게 항상 캐치해주는. 분야를 가리지 않고, 전반적인 프로젝트를 점검하고 통솔하는. <br/> 🛠️ 인프라 마스터 |

## 인프라 구조

### 클라이언트 요청 흐름도

<img src="https://user-images.githubusercontent.com/91937954/277229657-b86f5bf7-d735-4df0-96de-def921175d09.png" alt="클라이언트 요청 흐름도">

### CI/CD

<img src="https://user-images.githubusercontent.com/91937954/277229537-41fbe767-2bbc-4b3d-ae4b-39e933354d5f.png" alt="CI/CD">
