package com.learn.graphq.domain.bank;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Asset {
    UUID id;
    String name;
}
