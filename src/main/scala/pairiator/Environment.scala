package pairiator

trait Environment {
  def gitlabUrl: String = sys.env("GITLAB_API")
  def privateToken: String = sys.env("GITLAB_PRIVATE_TOKEN") // It's a teporary way, will be deprecated soon
}