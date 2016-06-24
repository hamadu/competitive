contest_path = ARGV[0]
package_name = ARGV[1]
problem_name = ARGV[2]

statement_file = "#{problem_name}.html"

`open #{statement_file}`
`rm renamer.rb`

