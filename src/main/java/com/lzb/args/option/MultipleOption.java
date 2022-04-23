package com.lzb.args.option;

public record MultipleOption(@Option("l") boolean logging,
                             @Option("p") int port,
                             @Option("d") String directory) {

}

