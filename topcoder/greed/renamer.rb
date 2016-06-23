contest_name = ARGV[0]
problem_name = ARGV[1]

puts ">" + contest_name
puts ">" + problem_name

head, count = contest_name.split(' ')
package = ["topcoder", "srm#{count.to_i/100}xx", "srm#{count}", "div1"]
src_path = ""
dst_path = "../" + package.join("/")
`mkdir -p #{dst_path}`

solution_file  = "#{problem_name}.java"
statement_file = "#{problem_name}.html"
sample_file    = "#{problem_name}.sample"

[solution_file, statement_file, sample_file].each do |file|
    if File.exist?("#{dst_path}/#{file}")
        `rm #{file}`
    else
        `mv #{file} #{dst_path}/#{file}`
    end
end

package_statement = "package " + package.join(".") + ";"
`sed -i "" -e "s:^package$:#{package_statement}:" #{dst_path}/#{solution_file}`

sample_file_path = package.join("/") + "/" + "#{sample_file}"
`sed -i "" -e "s:#{sample_file}:#{sample_file_path}:" #{dst_path}/#{solution_file}`

`open #{dst_path}/#{statement_file}`
`rm #{problem_name}.script`

