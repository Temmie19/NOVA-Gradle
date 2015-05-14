package nova.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

import java.util.regex.Matcher
import java.util.regex.Pattern

class NovaGradle implements Plugin<Project> {

	public static project;

	Pattern taskPattern = Pattern.compile("run(.+)(Server|Client)")

	@Override
	void apply(Project theProject) {
		this.project = theProject

		addWrapperTask(project)
	}

	def addWrapperTask(Project project) {
		project.tasks.addRule("Pattern: run<Wrapper>Server") {}
		project.tasks.addRule("Pattern: run<Wrapper>Client") { String taskName ->
			Matcher match = taskPattern.matcher(taskName)
			if (match.matches()) {
				def wrapper = match.group(1)
				def locality = Locality.fromString(match.group(2))

				WrapperManager.get(wrapper, locality, [:])
			}
		}
	}
}
