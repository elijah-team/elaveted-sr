package tripleo.elijah.foo;

//import tripleo.elijah_fluffy.annotation.builder.BuilderProperty;

public class Personify {

	private int age;

	private String name;

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	// @BuilderProperty
	public void setAge(int age) {
		this.age = age;
	}

//    @BuilderProperty
	public void setName(String name) {
		this.name = name;
	}

}
