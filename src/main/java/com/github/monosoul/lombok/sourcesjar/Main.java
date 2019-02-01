package com.github.monosoul.lombok.sourcesjar;

import lombok.val;

public final class Main {

	public static void main(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException("Wrong arguments!");
		}

		val pojo = SomePojo.builder()
				.someStringField(args[0])
				.anotherStringField(args[1])
				.build();

		System.out.println(pojo);
	}
}
