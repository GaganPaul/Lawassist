<?php
$studentName = "Mohan";
$skills = ["HTML5", "CSS3", "JavaScript", "PHP", "MySQL"];
$projects = [
    ["name" => "Portfolio Website", "description" => "Developed a personal portfolio website using HTML, CSS, and JavaScript."],
    ["name" => "Task Management App", "description" => "Created a task management web application with PHP and MySQL."]
];
$email = "mohan@kristujayanti.com";
$linkedin = "linkedin.com/in/mohan";
$github = "github.com/mohan";
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Portfolio</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            margin: 20px;
        }

        header {
            text-align: center;
            padding: 20px;
            background-color: #3498db;
            color: #fff;
        }

        section {
            margin: 20px 0;
        }

        h2 {
            color: #3498db;
        }

        ul {
            list-style-type: none;
            padding: 0;
        }

        li {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <header>
        <h1>Student Portfolio</h1>
    </header>

    <section>
        <h2>About Me</h2>
        <p>Hello! My name is <?php echo htmlspecialchars($studentName); ?>, and I am a passionate student interested in programming and web development.</p>
    </section>

    <section>
        <h2>Skills</h2>
        <ul>
            <?php foreach ($skills as $skill): ?>
                <li><?php echo htmlspecialchars($skill); ?></li>
            <?php endforeach; ?>
        </ul>
    </section>

    <section>
        <h2>Projects</h2>
        <ul>
            <?php foreach ($projects as $project): ?>
                <li>
                    <strong><?php echo htmlspecialchars($project['name']); ?></strong>
                    <p><?php echo htmlspecialchars($project['description']); ?></p>
                </li>
            <?php endforeach; ?>
        </ul>
    </section>

    <section>
        <h2>Contact</h2>
        <p>Email: <?php echo htmlspecialchars($email); ?></p>
        <p>LinkedIn: <?php echo htmlspecialchars($linkedin); ?></p>
        <p>GitHub: <?php echo htmlspecialchars($github); ?></p>
    </section>
</body>
</html>

