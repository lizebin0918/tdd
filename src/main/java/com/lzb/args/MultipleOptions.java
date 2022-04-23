package com.lzb.args;

public record MultipleOptions(@Option("l") boolean logging,
                              @Option("p") int port,
                              @Option("d") String directory) {

}

