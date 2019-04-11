import * as React from 'react';

import styled from 'styled-components';
import { NavLink } from 'react-router-dom';

const categories = [
  {
    name: 'all',
    text: '전체보기'
  },
  {
    name: 'beaminz',
    text: '배민라이더스'
  },
  {
    name: 'baeminchan',
    text: '배민찬'
  },
  {
    name: 'sports',
    text: '스포츠'
  },
  {
    name: 'technology',
    text: '기술'
  },
  {
    name: 'aaaaauuuaa',
    text: '아아으아아'
  }
];

const EventCategoriesBlock = styled.div`
  display: flex;
  padding: 1rem;
  width: 768px;
  margin: 0 auto;
  @media screen and (max-width: 768px) {
    width: 100%;
    overflow-x: auto;
  }
`;

const EventCategory = styled(NavLink)`
  font-size: 1.125rem;
  cursor: pointer;
  white-space: pre;
  text-decoration: none;
  color: inherit;

  &:hover {
    color: #495057;
  }

  &.active {
    font-weight: 600;
    border-bottom: 2px solid #22b8cf;
    color: #22b8cf;
    &:hover {
      color: #3bc9db;
    }
  }

  & + & {
    margin-left: 1rem;
  }
`;

export default class EventCategories extends React.Component<{}, any> {
  public render() {
    return (
      <EventCategoriesBlock>
        {categories.map(c => (
          <EventCategory
            key={c.name}
            activeClassName="active"
            exact={c.name === 'all'}
            to={c.name === 'all' ? '/' : `/${c.name}`}
          >
            {c.text}
          </EventCategory>
        ))}
      </EventCategoriesBlock>
    );
  }
}
