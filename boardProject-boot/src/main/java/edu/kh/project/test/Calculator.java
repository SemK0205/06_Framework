package edu.kh.project.test;

// 계산기 기능 클래스
public class Calculator {

	// 더하기
	public int add(int a, int b) {
		return a + b;
	}
	
	// 빼기
	public int subtract(int a, int b) {
		return a - b;
	}
	
	// 곱하기
	public int multiply(int a, int b) {
		return a * b;
	}
	
	// 나누기
	public int divide(int a, int b) {
		if (b == 0) {
			throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
		}
		return a / b;
	}
	
}
