package de.schulung.java;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaDemo {

  static double add(double a, double b) {
    return a + b;
  }

  static double add2() {
    double sum = 0;
    while (sum <= 10) {
      sum = sum + Math.random();
    }
    return sum;
  }

  /*
   * Funktionales Interface = Interface mit genau einer abstrakten Methode
   */
  @FunctionalInterface
  interface NumberGenerator {
    double next();
  }

  static double add3(NumberGenerator generator) {
    double sum = 0;
    while (sum <= 10) {
      sum = sum + generator.next();
    }
    return sum;
  }

  static double add4(DoubleSupplier generator) {
    double sum = 0;
    while (sum <= 10) {
      sum = sum + generator.getAsDouble();
    }
    return sum;
  }

  public static void main(String[] args) {
    // Addiere 2 Zahlen
    System.out.println(add(2.4, 3.6));
    // Addiere 2 Zufallszahlen
    System.out.println(add(Math.random(), Math.random()));
    // Addiere Zufallszahlen so lange, bis die Summe > 10
    System.out.println(add2());
    // Die Entscheidung für Math.random() soll in main bleiben
    System.out.println(add3(new NumberGenerator() {
      @Override
      public double next() {
        return Math.random();
      }
    }));
    System.out.println(add3(new NumberGenerator() {
      @Override
      public double next() {
        return 0.3;
      }
    }));
    // Lambdas: Verkürzung durch Entfernen von Redundanzen (Compiler)
    System.out.println(add3(/*new NumberGenerator() {
      @Override
      public double next*/() -> {
        return Math.random();
      }
      /*}*/));
    System.out.println(add3(() -> {
      return Math.random();
    }));
    // Sonderfall: nur 1 Anweisung in der Methode
    System.out.println(add3(() -> Math.random()));
    // Sonderfall: Parameter für next und random matchen (beide ohne Parameter)
    System.out.println(add3(Math::random)); // :: Method Reference Operator
    // Vgl.
    System.out.println(add(Math.random(), Math.random()));

    System.out.println(add3(() -> 0.3)); // :: Method Reference Operator

    // Funktionale Interface von Java
    Supplier<Double> s = new Supplier<Double>() {
      @Override
      public Double get() {
        return Math.random();
      }
    };
    s = Math::random;
    var random = s.get();
    Consumer<String> c = new Consumer<String>() {
      @Override
      public void accept(String s) {
        System.out.println(s);
      }
    };
    c = text -> System.out.println(text);
    c = System.out::println;

    c.accept("Hello World");

    Function<String, Integer> f = new Function<String, Integer>() {
      @Override
      public Integer apply(String s) {
        return Integer.valueOf(s);
      }
    };
    f = text -> Integer.valueOf(text);
    f = Integer::valueOf;
    System.out.println(f.apply("123"));

    Predicate<String> p = new Predicate<String>() {
      @Override
      public boolean test(String s) {
        return s != null && s.length() > 2;
      }
    };
    p = text -> text != null && text.length() > 2;
    System.out.println(p.test("Test"));

    System.out.println(add4(Math::random));

    // Beispiel mit Streams/Collections
    List<String> namen = List.of("Max", "David", "Eugen", "Moritz", "Ralf");
    // Anforderung: Kurze Namen (<5 Buchstaben) in Großbuchstaben
    {
      List<String> kurzNamen = new ArrayList<>();
      for (String name : namen) {
        if (name.length() < 5) {
          kurzNamen.add(name.toUpperCase());
        }
      }
      System.out.println(kurzNamen);
    }
    {
      List<String> kurzNamen = namen
        .stream()
        .filter(name -> name.length() < 5)
        .map(String::toUpperCase)
        .toList();
      System.out.println(kurzNamen);
    }
    System.out.println(
      namen
        .stream()
        .filter(name -> name.length() < 5)
        .map(String::toUpperCase)
        .findFirst()
        .orElseGet(() -> "Kein Kurznamen gefunden")
    );
  }

}
