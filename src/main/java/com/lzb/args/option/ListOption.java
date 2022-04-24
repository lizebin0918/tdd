package com.lzb.args.option;

public record ListOption(@Option("g") String[] group, @Option("d") Integer[] decimals) {

}