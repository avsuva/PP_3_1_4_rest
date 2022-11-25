async function thisUser() {
    fetch("http://localhost:8080/api/user")
        .then(res => res.json())
        .then(data => {
            $('#headerEmail').append(data.email);
            let roles = data.roles.map(role => " " + role.role.substring(5));
            $('#headerRoles').append(roles);

            let user = `$(
            <tr>
                <td>${data.id}</td>
                <td>${data.username}</td>
                <td>${data.email}</td>
                <td>***********</td>
                <td>${roles}</td>)`;
            $('#userPanelBody').append(user);
        })
}

$(async function() {
    await thisUser();
});