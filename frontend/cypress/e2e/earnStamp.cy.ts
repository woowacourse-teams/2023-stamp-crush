describe('스탬프 적립 시나리오', () => {
  beforeEach(() => {
    localStorage.setItem('admin-login-token', 'warfgsfdgfsgr');
    cy.visit('http://localhost:3000/admin');
  });

  it('기존에 스탬프 3개를 가지고 있는 회원이 2개를 적립한다.', async () => {
    cy.visit('http://localhost:3000/admin/enter-stamp');
    cy.get('#phoneNumber').type('11112222');
    cy.contains(/^입력$/).click();

    cy.contains(/^다음$/).click();
    cy.contains(/^\+$/).click();
    cy.contains(/^적립$/).click();

    cy.contains('li', '윤생').contains('span', '스탬프').should('contain', '5/10');
  });
});
