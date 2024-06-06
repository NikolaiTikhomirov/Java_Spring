package ru.gb;

import lombok.Getter;

@Getter
public class Item {

  private final int index;
  private final String name;

  public Item(int index, String name) {
    this.index = index;
    this.name = name;
  }
}
