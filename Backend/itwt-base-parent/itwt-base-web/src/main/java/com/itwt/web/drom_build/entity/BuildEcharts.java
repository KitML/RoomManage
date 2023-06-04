package com.itwt.web.drom_build.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class BuildEcharts {
    private List<String> names = new ArrayList<>();
    private List<Integer> counts = new ArrayList<>();
}