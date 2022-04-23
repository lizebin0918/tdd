package com.lzb.args;

public record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {

}