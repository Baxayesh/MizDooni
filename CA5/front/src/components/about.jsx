import Table from "../image/tables.png";

const About = () => {
    return (
        <>
            <div id="sec3" className="container">
            <section className="row" style={{marginBottom:"30px"}}>
                <article className="col-xs-12 col-md-6">
                    <img src={Table} alt="tables-pic" className="img-responsive" />
                </article>
                <article className="col-xs-12 col-md-6">
                    <h3 style={{color:"#ED3545"}}>About Mizdooni</h3>
                    <p>Are you tired of waiting in long lines at restaurants or struggling to find a table at your favorite eatery?</p>
                    <p> Look no further than Mizdooni – the ultimate solution for all your dining reservation needs.</p>
                    <p>Mizdooni is a user-friendly website where you can reserve a table at any restaurant, from anywhere, at a specific time. Whether you're craving sushi, Italian, or a quick bite to eat, Mizdooni has you covered.</p>
                    <p>With a simple search feature, you can easily find a restaurant based on cuisine or location.</p>
                    <p><span style={{color:"#ED3545"}}>The best part?</span> There are no fees for making a reservation through Mizdooni. Say goodbye to the hassle of calling multiple restaurants or showing up only to find there's a long wait. With Mizdooni, you can relax knowing that your table is secured and waiting for you.</p>
                    <p>Don't let dining out be a stressful experience. Visit Mizdooni today and start enjoying your favorite meals without the headache of making reservations. Reserve your table with ease and dine with peace of mind.</p>

                </article>
            </section>
        </div>
        </>
    );
}

export default About;